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
                            { "resume": "BWwRWJIIlxlMO1R24-KSWxD1KqBjILlVGArnmtG9uU5" }
                        ]
                    }
                """.trimIndent())
            } else if (json.has("msg") && json.getString("msg") == "result" && json.getString("id") == "42") {
                Log.i("WebSocket", "Login successful!")
                webSocketManager?.sendMessage("""
                    {
                        "msg": "method",
                        "method": "rooms/get",
                        "id": "42",
                        "params": [ { date: 1480377601 } ]
                    }
                """.trimIndent())
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
