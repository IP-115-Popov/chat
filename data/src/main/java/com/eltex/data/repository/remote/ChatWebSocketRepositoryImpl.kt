package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ChatApi
import com.eltex.data.mappers.ChatResultToChatModelMapper
import com.eltex.data.models.chat.ChatResponse
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.ChatRemoteRepository
import com.eltex.domain.websocket.WebSocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import javax.inject.Inject

class ChatWebSocketRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val chatApi: ChatApi,
) : ChatRemoteRepository {

    private val jsonSerializator = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getChats(): Flow<List<ChatModel>> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            Log.i("ChatRepositoryImpl", json.toString())
            try {
                if (json.has("result")) {
                    try {
                        Log.i("gson", json.toString())
                        val result =
                            jsonSerializator.decodeFromString<ChatResponse>(json.toString()).result

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

        awaitClose {
            webSocketManager.removeListener(listener)
            Log.d("ChatRepository", "Flow closed, listener removed")
        }
    }

    override suspend fun getChatInfo(roomId: String): Either<DataError, ChatModel> {
        return try {
            val response = chatApi.getChatInfo(roomId = roomId)
            if (response.isSuccessful) {
                val room = response.body()?.room
                if (room != null) {
                    ChatModel(
                        id = room._id,
                        name = room.fname ?: room.name,
                        usernames = room.usernames,
                        uids = room.uids,
                        t = room.t ?: "",
                        usersCount = room.usersCount,
                    ).right()
                } else {
                    DataError.DefaultError.left()
                }
            } else {
                when (response.code()) {
                    in 400 until 500 -> DataError.IncorrectData.left()
                    else -> DataError.DefaultError.left()
                }
            }
        } catch (e: Exception) {
            Log.e("ChatRemoteRepository", "getChat ${e.message}")
            e.printStackTrace()
            DataError.ConnectionMissing.left()
        }
    }
}
