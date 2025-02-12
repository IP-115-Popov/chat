package com.eltex.data.websockets

import android.util.Log
import com.eltex.data.models.chat.ChatResponse
import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.ChatRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


data class aboba (
   val  msg: String,
   val  id: String,
   val  result: ChatResponse,
)
internal class IgnoreFailureTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)
        return createCustomTypeAdapter(delegate)
    }

    private fun <T> createCustomTypeAdapter(delegate: TypeAdapter<T>): TypeAdapter<T> {
        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter?, value: T) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): T? {
                try {
                    return delegate.read(`in`)
                } catch (e: java.lang.Exception) {
                    `in`.skipValue()
                    return null
                }
            }
        }
    }
}
class ChatRepositoryImpl @Inject constructor(
    private val webSocketManager: WebSocketManager
) : ChatRepository {

    private val gson = GsonBuilder()
        .setLenient()
        .registerTypeAdapterFactory(IgnoreFailureTypeAdapterFactory())
        .create();

    override suspend fun getChat(): Flow<ChatModel> = callbackFlow {
        val listener: (JSONObject) -> Unit = { json ->
            Log.i("ChatRepositoryImpl", json.toString())
            try {
                Log.i("gson", "(json.has(\"fname\"")
                if (json.has("result")) {
                    try {
                        Log.i("gson",json.toString())
                        val result = gson.fromJson(json.toString(), ChatResponse::class.java).result.firstOrNull()

                        if (result != null) {
                            trySend(ChatModel(
                                id = result._id,
                                name = result.fname ?: "",
                                lastMessage = result.lastMessage.msg,
                                 lm= result.lm.`$date`.toString(),
                                 unread=result.usersCount,
                                avatarUrl=result.avatarETag,
                            ))
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
}
