package com.eltex.data.repository.remote

import android.util.Log
import com.eltex.data.mappers.MessageResponseToMessageMapper
import com.eltex.data.models.message.MessageResponse
import com.eltex.domain.models.Message
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.websocket.WebSocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

class ChatMessageWebSocketRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager
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

    private fun generateSubscriptionId(): String {
        return "sub-" + UUID.randomUUID().toString()
    }
}