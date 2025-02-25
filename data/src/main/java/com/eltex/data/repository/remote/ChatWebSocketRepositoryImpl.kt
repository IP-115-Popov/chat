package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ChatApi
import com.eltex.data.mappers.AttachmentsToFileModelMapper
import com.eltex.data.mappers.ChatResultToChatModelMapper
import com.eltex.data.models.chat.ChatResponse
import com.eltex.data.models.usernotify.RoomInserted
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.repository.remote.ChatRemoteRepository
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
                            trySend(result.map { chat ->
                                ChatResultToChatModelMapper.map(chat)
                            })
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

    override suspend fun subscribeToChats(userId: String): Flow<ChatModel> = callbackFlow {

        val listener: (JSONObject) -> Unit = { json ->
            try {
                if (json.getString("msg") == "changed" && json.getString("collection") == "stream-notify-user") {
                    val fields = json.getJSONObject("fields")
                    val eventName = fields.getString("eventName")

                    if (eventName.endsWith("/rooms-changed")) {
                        val args = fields.getJSONArray("args")
                        val eventType = args.getString(0)
                        val roomDataJson = args.getJSONObject(1).toString()

                        try {
                            when (eventType) {
                                "updated" -> {
                                    val roomData =
                                        jsonSerializator.decodeFromString<RoomInserted>(roomDataJson)
                                    val message = roomData.lastMessage?.let { lastMessage ->
                                        val fileModel: FileModel? =
                                            lastMessage.attachments?.mapNotNull { jsonElement ->
                                                try {
                                                    return@mapNotNull AttachmentsToFileModelMapper.map(
                                                        jsonElement
                                                    )
                                                } catch (e: Exception) {
                                                    println("Error parsing attachment: ${e.message}")
                                                    return@mapNotNull null
                                                }
                                            }?.firstOrNull()

                                        Message(
                                            id = lastMessage._id,
                                            rid = lastMessage.rid,
                                            msg = lastMessage.msg ?: "",
                                            date = lastMessage._updatedAt?.`$date` ?: 0,
                                            userId = lastMessage.u?._id ?: "",
                                            name = lastMessage.u?.name ?: lastMessage.u?.username
                                            ?: "",
                                            username = lastMessage.u?.username ?: "",
                                            fileModel = fileModel,
                                        )
                                    }

                                    trySend(
                                        ChatModel(
                                            id = roomData._id,
                                            name = roomData.fname,
                                            usernames = roomData.usernames,
                                            uids = roomData.uids,
                                            t = roomData.t,
                                            usersCount = roomData.usersCount,
                                            message = message,
                                            lm = null,
                                            unread = null,
                                            avatarUrl = null,
                                        )
                                    )
                                }

                                "inserted" -> {
                                    val roomData =
                                        jsonSerializator.decodeFromString<RoomInserted>(roomDataJson)
                                    trySend(
                                        ChatModel(
                                            id = roomData._id,
                                            name = roomData.fname,
                                            usernames = roomData.usernames,
                                            uids = roomData.uids,
                                            t = roomData.t,
                                            usersCount = roomData.usersCount,
                                            message = null,
                                            lm = null,
                                            unread = null,
                                            avatarUrl = null,
                                        )
                                    )
                                }

                                else -> {
                                    Log.w("WebSocket", "Unknown event type: $eventType")
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("WebSocked", "ChatMessageRepository json error: ${e.message}", e)
                        }
                    } else {
                        Log.d("WebSocket", "Unhandled eventName: $eventName")
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
                        "name": "stream-notify-user",
                        "params":[
                            "$userId/rooms-changed",
                            false
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

    private fun generateSubscriptionId(): String {
        return "sub-" + UUID.randomUUID().toString()
    }
}
