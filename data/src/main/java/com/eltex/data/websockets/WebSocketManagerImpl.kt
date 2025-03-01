package com.eltex.data.websockets

import android.util.Log
import com.eltex.domain.websocket.WebSocketConnectionState
import com.eltex.domain.websocket.WebSocketManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManagerImpl @Inject constructor() : WebSocketManager {
    private var webSocketManager: RocketChatWebSocket? = null

    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    override val connectionState: Flow<WebSocketConnectionState> = _connectionState.asStateFlow()

    private val listeners: MutableList<(JSONObject) -> Unit> = mutableListOf()


    init {
        Log.i("WebSocketManagerImpl", "WebSocketManagerImpl Created!!!")

        val listener = RocketChatWebSocketListener { json ->
            if (json.has("failListener")) {
                _connectionState.update {
                    Log.i("WebSocketManager", "fail")
                    WebSocketConnectionState.Error
                }
            }

            if (json.has("msg")) {
                when (json.getString("msg")) {
                    "ping" -> {
                        webSocketManager?.sendMessage("""{"msg": "pong"}""")
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
                            _connectionState.update {
                                Log.i("WebSocketManagerImpl", "connected")
                                WebSocketConnectionState.Connected
                            }
                        }
                    }

                    else -> {
                        Log.d("WebSocket", "Unhandled message: ${json.toString()}")
                    }
                }
            }
            runCatching {
                listeners.forEach { it(json) }
            }
        }
        webSocketManager = RocketChatWebSocket(listener)
    }

    override fun addListener(listener: (JSONObject) -> Unit) {

        listeners.add(listener)
    }

    override fun removeListener(listener: (JSONObject) -> Unit) {
        listeners.remove(listener)
    }

    override fun sendMessage(json: String) {
        if (_connectionState.value is WebSocketConnectionState.Connected) {
            webSocketManager?.sendMessage(json)
        }
    }

    override fun connect(authToken: String) {
        Log.i("WebSocketManagerImpl", "connect init")
        if (_connectionState.value !is WebSocketConnectionState.Connected) {
            Log.i("WebSocketManagerImpl", "connect start")

            addListener { json ->
                if (json.has("msg")) {
                    if (json.getString("msg") == "connected") {
                        webSocketManager?.sendMessage(
                            """
                            {
                                "msg": "method",
                                "method": "login",
                                "id": "42",
                                "params": [
                                    { "resume": "$authToken" }
                                ]
                            }
                        """.trimIndent()
                        )
                    }
                }
            }
            _connectionState.update {
                Log.i("WebSocketManagerImpl", "connecting")
                WebSocketConnectionState.Connecting
            }
            webSocketManager?.connect()
        }
    }

    override fun disconnect() {
        webSocketManager?.close()
        _connectionState.update {
            WebSocketConnectionState.Disconnected
        }
    }
}