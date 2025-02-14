package com.eltex.data.repository

import android.util.Log
import com.eltex.data.mappers.MessageResponseToMessageMapper
import com.eltex.data.models.message.MessageResponse
import com.eltex.domain.models.Message
import com.eltex.domain.repository.ChatMessageRepository
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

class ChatMessageRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager
): ChatMessageRepository {

    private val jsonSerializator = Json {
        ignoreUnknownKeys = true
    }

    // Сохраняем id подписки, чтобы можно было отписаться.
    private var subscriptionId: String? = null

    // Функция для подписки на поток сообщений комнаты
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

        val id = generateSubscriptionId() // Генерация уникального id для подписки

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
            // Отписываемся при закрытии flow
            unsubscribeFromRoomMessages(roomId)
            webSocketManager.removeListener(listener)
            Log.d("RoomMessages", "Flow closed, listener removed")
        }
    }

    // Функция для отписки от потока сообщений комнаты
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