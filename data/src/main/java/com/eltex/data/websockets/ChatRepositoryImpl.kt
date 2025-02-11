package com.eltex.data.websockets

import android.util.Log
import com.eltex.domain.repository.ChatDTO
import com.eltex.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager
) : ChatRepository {

    override suspend fun getChat(): Flow<ChatDTO> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            Log.i("ChatRepositoryImpl", json.toString())
//            try {
//                if (json.has("msg") && json.getString("msg") == "result") {
//                    if (json.has("result")) {
//                        val result = json.getJSONObject("result")
//                        val chatDTO = parseChatDTO(result)
//                        trySend(chatDTO)
//
//                    } else {
//                        Log.w("ChatRepository", "No 'result' field in JSON: $json")
//                        close(Exception("No 'result' field in JSON"))
//                    }
//                } else {
//                    Log.d("ChatRepository", "Unhandled message: $json")
//                }
//            } catch (e: Exception) {
//                Log.e("ChatRepository", "Error processing JSON: $json", e)
//                close(e)
//            }
        }

        webSocketManager.addListener(listener)

        // Отправляем запрос на получение чата
        withContext(Dispatchers.IO) {
            delay(5000)
            Log.i("ChatRepositoryImpl", "sendMessage get Chat")
            webSocketManager.sendMessage(
                """
                {
                    "msg": "method",
                    "method": "rooms/get",
                    "id": "42",
                    "params": [ { "data": 0 } ]
                }
            """.trimIndent()
            )
        }

        awaitClose {
            webSocketManager.removeListener(listener)
            Log.d("ChatRepository", "Flow closed, listener removed")
        }
    }

    private fun parseChatDTO(json: JSONObject): ChatDTO {
        val id = json.getString("_id")
        val name = json.getString("name")

        return ChatDTO(id = id, name = name, lastMessage = "")
    }
}
