package com.eltex.data.repository.remote

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatMessageWebSocketRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val chatCommunicationApi: ChatCommunicationApi,
    @ApplicationContext private val context: Context,
) : ChatMessageRemoteRepository {

    private val jsonSerializator = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun subscribeToRoomMessages(roomId: String): Flow<Message> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->

            try {
                if (json.has("msg") && json.getString("msg") == "changed" && json.getString("collection") == "stream-room-messages") {

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
        }

        awaitClose {
            unsubscribeFromRoomMessages(id)
            webSocketManager.removeListener(listener)
            Log.d("RoomMessages", "Flow closed, listener removed")
        }
    }.shareIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )

    override suspend fun subscribeToRoomDeleteMessagesId(roomId: String): Flow<String> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            try {
                if (json.has("msg") && json.getString("msg") == "changed" && json.getString("collection") == "stream-notify-room") {
                    val fields = json.optJSONObject("fields")
                    if (fields != null) {
                        val eventName = fields.optString("eventName", "")
                        if (eventName.endsWith("/deleteMessage")) {
                            val args = fields.optJSONArray("args")
                            if (args != null && args.length() > 0) {
                                val firstArg = args.optJSONObject(0)
                                if (firstArg != null) {
                                    val messageId = firstArg.optString("_id", "")
                                    if (messageId.isNotEmpty()) {
                                        trySend(messageId)
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("RoomMessages", "Error processing message: ${e.message}", e)
            }
        }

        webSocketManager.addListener(listener)

        val id = generateSubscriptionId()

        withContext(Dispatchers.IO) {
            webSocketManager.sendMessage(
                """
                {
                    "msg": "sub",
                    "id": "$id",
                    "name": "stream-notify-room",
                    "params":[
                        "$roomId/deleteMessage",
                        false
                    ]
                }
                """.trimIndent()
            )
        }



        awaitClose {
            unsubscribeFromRoomMessages(id)
            webSocketManager.removeListener(listener)
            Log.d("RoomDeleteMessagesId", "Flow closed, listener removed")
        }
    }.shareIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(1000),
        replay = 1
    )

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
            val uri = Uri.parse(messagePayload.uri)
            val mimeType = context.contentResolver.getType(uri)
            val fileName = getFileName(context, uri) ?: "file"

            val filePart = MultipartBody.Part.createFormData(
                name = "file",
                filename = fileName,
                requireNotNull(context.contentResolver.openInputStream(uri)).use {
                    it.readBytes()
                }.toRequestBody(mimeType?.toMediaTypeOrNull()),
            )

            return chatCommunicationApi.uploadFile(roomId, filePart, descriptionBody)
        }
    }

    override suspend fun deleteMessages(roomId: String, msgId: String) {
        try {
            chatCommunicationApi.deleteMessage(roomId = roomId, msgId = msgId)
        } catch (e: Exception) {
            Log.d("RoomDeleteMessagesId", "deleteMessages error ${e.message}")
            e.printStackTrace()
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex >= 0) {
                    fileName = it.getString(displayNameIndex)
                }
            }
        }
        return fileName
    }

    private fun unsubscribeFromRoomMessages(id: String) {
        webSocketManager.sendMessage(
            """
                {
                    "msg": "unsub",
                    "id": "$id"
                }
            """.trimIndent()
        )
    }

    private fun generateSubscriptionId(): String {
        return "sub-" + UUID.randomUUID().toString()
    }
}