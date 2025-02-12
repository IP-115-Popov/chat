package com.eltex.domain.websocket

import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface WebSocketManager {
    val connectionState: Flow<WebSocketConnectionState>

    fun connect()

    fun disconnect()

    fun addListener(listener: (JSONObject) -> Unit)

    fun removeListener(listener: (JSONObject) -> Unit)

    fun sendMessage(json: String)
}