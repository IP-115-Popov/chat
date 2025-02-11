package com.eltex.data.websockets

import android.util.Log
import org.json.JSONObject
import javax.inject.Inject

class WebSocketManager @Inject constructor() {

    private var webSocketManager: RocketChatWebSocket? = null
    var isConnected = false

    private val listeners: MutableList<(JSONObject) -> Unit> = mutableListOf()

    init {
        val listener = RocketChatWebSocketListener { json ->
            if (json.has("msg")) {
                when (json.getString("msg")) {
                    "ping" -> {
                        webSocketManager?.sendMessage("""{"msg": "pong"}""")
                    }

                    "connected" -> {
                        webSocketManager?.sendMessage(
                            """
                            {
                                "msg": "method",
                                "method": "login",
                                "id": "42",
                                "params": [
                                    { "resume": "BWwRWJIIlxlMO1R24-KSWxD1KqBjILlVGArnmtG9uU5" }
                                ]
                            }
                        """.trimIndent()
                        )
                    }

                    "result" -> {
                        if (json.has("id") && json.getString("id") == "42") {
                            if (json.has("error")) {
                                val error = json.getJSONObject("error")
                                Log.e("WebSocket", "Login failed: ${error.getString("message")}")
                            } else {
                                Log.i("WebSocket", "Login successful!")
                            }
                        }
                    }
                    "added" -> {
                        if (json.has("collection") && json.getString("collection") == "users") {
                            isConnected = true
                        }
                    }
                    else -> {
                        Log.d("WebSocket", "Unhandled message: ${json.toString()}")
                    }
                }
            }
            listeners.forEach { it(json) }
        }
        webSocketManager = RocketChatWebSocket(listener)
        webSocketManager?.connect()
    }

    fun addListener(listener: (JSONObject) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (JSONObject) -> Unit) {
        listeners.remove(listener)
    }

    fun sendMessage(json: String) {
        if (isConnected) {
            webSocketManager?.sendMessage(json)
        }
    }
}