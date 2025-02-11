package com.eltex.data.websockets

import android.util.Log
import com.eltex.domain.repository.ChatDTO
import com.eltex.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor() : ChatRepository {

    private var webSocketManager: RocketChatWebSocket? = null

    init {
        val listener = RocketChatWebSocketListener { json ->
            //Обработка входящего сообщения
            if (json.has("msg") && json.getString("msg") == "ping") {
                webSocketManager?.sendMessage("""{"msg": "pong"}""")
            } else if (json.has("msg") && json.getString("msg") == "connected") {
                // После подключения отправляем сообщение login
                // ВНИМАНИЕ: Замените username и password на реальные значения!
                webSocketManager?.sendMessage("""
                    {
                        "msg": "method",
                        "method": "login",
                        "id": "42",
                        "params": [
                            {
                                "user": {
                                    "username": "him"
                                },
                                "password": {
                                     "digest": "52c53f4abbfe42e1ccd4fd9d864453ee57f8efbd4c9ecec6d88bd83d7f7a9c02",
                                     "algorithm":"sha-256"
                                }
                            }
                        ]
                    }
                """.trimIndent())
            } else if (json.has("msg") && json.getString("msg") == "result" && json.getString("id") == "42") {
                Log.i("WebSocket", "Login successful!")
                // Теперь можно отправлять другие команды, например, subscribe
                // webSocketManager?.sendMessage("""{"msg": "sub", "id": "1", "name": "stream-room-messages", "params": ["GENERAL"]}""")
            }
        }
        webSocketManager = RocketChatWebSocket(listener)
        webSocketManager?.connect()
    }

    override suspend fun getChat(): Flow<ChatDTO> {
        //getRooms(webSocket: WebSocket)
        return listOf(ChatDTO(name = "", id = "", lastMessage = "")).asFlow()
    }
}
