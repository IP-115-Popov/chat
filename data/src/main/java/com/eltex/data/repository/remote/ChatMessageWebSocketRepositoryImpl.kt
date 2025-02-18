package com.eltex.data.repository.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.eltex.data.api.ChatCommunicationApi
import com.eltex.data.mappers.MessageResponseToMessageMapper
import com.eltex.data.models.communication.MessageForCommunication
import com.eltex.data.models.communication.TextMessagePayload
import com.eltex.data.models.lifemessage.MessageResponse
import com.eltex.domain.models.Message
import com.eltex.domain.models.MessagePayload
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.websocket.WebSocketManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

class ChatMessageWebSocketRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val chatCommunicationApi: ChatCommunicationApi,
    @ApplicationContext private val context: Context,
) : ChatMessageRemoteRepository {

    private val jsonSerializator = Json {
        ignoreUnknownKeys = true
    }

    private var subscriptionId: String? = null

    override suspend fun subscribeToRoomMessages(roomId: String): Flow<Message> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            Log.d("RoomMessages", "Raw JSON: $json")

            try {
                if (json.getString("msg") == "changed" && json.getString("collection") == "stream-room-messages") {

                    val fields = json.getString("fields")

                    try {
                        val result = jsonSerializator.decodeFromString<MessageResponse>(fields)
                        trySend(MessageResponseToMessageMapper.map(result))
                    } catch (e: Exception) {
                        Log.e("WebSocked", "ChatMessageRepository json error: ${e.message}", e)
                    }
                }

            } catch (e: Exception) {
                Log.e("RoomMessages", "Error processing message: ${e.message}", e)
            }
        }

        webSocketManager.addListener(listener)

        val id = generateSubscriptionId()

        // Отправляем запрос на подписку
        withContext(Dispatchers.IO) {
            webSocketManager.sendMessage(
                """
                {
                    "msg": "sub",
                    "id": "$id",
                    "name": "stream-room-messages",
                    "params": [
                        "$roomId",
                        false
                    ]
                }
                """.trimIndent()
            )
            subscriptionId = id
        }


        awaitClose {
            unsubscribeFromRoomMessages(roomId)
            webSocketManager.removeListener(listener)
            Log.d("RoomMessages", "Flow closed, listener removed")
        }
    }

    override suspend fun sendMessages(messagePayload: MessagePayload) {
        if (messagePayload.uri == null) {
            //нет вложений обычное сообщение
            chatCommunicationApi.sendTextMessage(
                textMessagePayload = TextMessagePayload(
                    message = MessageForCommunication(
                        msg = messagePayload.msg,
                        rid = messagePayload.roomId,
                    ),
                )
            )
        } else {
            val roomId = messagePayload.roomId
            val descriptionBody =
                messagePayload.msg.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val filePart = MultipartBody.Part.createFormData(
                name = "file",
                filename = "file",
                requireNotNull(context.contentResolver.openInputStream(Uri.parse(messagePayload.uri))).use {
                    it.readBytes()
                }.toRequestBody()
            )

            return chatCommunicationApi.uploadFile(roomId, filePart, descriptionBody)
        }
    }

    private fun unsubscribeFromRoomMessages(roomId: String) {
        val id = subscriptionId ?: return

        webSocketManager.sendMessage(
            """
                {
                    "msg": "unsub",
                    "id": "$id"
                }
            """.trimIndent()
        )
        subscriptionId = null
        Log.d("RoomMessages", "Unsubscribed from room: $roomId")
    }

    private fun generateMethodId(): String {
        return "method-" + UUID.randomUUID().toString()
    }

    private fun generateSubscriptionId(): String {
        return "sub-" + UUID.randomUUID().toString()
    }
}