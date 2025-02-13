package com.eltex.data.repository

import android.util.Log
import com.eltex.data.mappers.ChatResultToChatModelMapper
import com.eltex.data.models.chat.ChatResponse
import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.ChatRepository
import com.eltex.domain.websocket.WebSocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager
) : ChatRepository {

    private val jsonSerializator = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun createChat(chatName: String) : Flow<List<ChatModel>> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
                if (json.has("result")) {
                    Log.i("gson", json.toString())
                }
        }

        webSocketManager.addListener(listener)

        Log.i("createPrivateGroup", "createPrivateGroup")
        // Отправляем запрос на получение чата
        withContext(Dispatchers.IO) {
            webSocketManager.sendMessage(
                """
                {
                    "msg": "method",
                    "method": "createPrivateGroup",
                    "id": "42",
                    "params": [ 
                        "$chatName"
                    ]
                }
            """.trimIndent()
            )
        }



        awaitClose {
            webSocketManager.removeListener(listener)
            Log.d("ChatRepository", "Flow closed, listener removed")
        }
    }

    override suspend fun getChat(): Flow<List<ChatModel>> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            Log.i("ChatRepositoryImpl", json.toString())
            try {
                if (json.has("result")) {
                    try {
                        Log.i("gson",json.toString())
                        val result = jsonSerializator.decodeFromString<ChatResponse>(json.toString()).result

                        if (result.isNotEmpty()) {
                            trySend(
                                result.map { chat ->
                                    ChatResultToChatModelMapper.map(chat)
                                }
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("ChatRepository", "Gson error: ${e.message}", e)
                    }

                } else {
                    Log.d("ChatRepository", "Unhandled message: $json")
                }
            } catch (e: Exception) {
                Log.e("ChatRepository", "Error processing JSON: $json", e)
            }
        }

        webSocketManager.addListener(listener)

        // Отправляем запрос на получение чата
        withContext(Dispatchers.IO) {
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

        withContext(Dispatchers.IO) {
            webSocketManager.sendMessage(
                """
                {
                     "msg": "sub",
                      "id": "2",
                      "name": "users.list",
                      "params": [
                            {
                              "count": 20,
                              "offset": 0
                            }
                      ]
                }
            """.trimIndent()
            )
        }

        awaitClose {
            webSocketManager.removeListener(listener)
            Log.d("ChatRepository", "Flow closed, listener removed")
        }
    }
}
