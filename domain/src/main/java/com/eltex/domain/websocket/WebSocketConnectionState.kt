package com.eltex.domain.websocket

sealed class WebSocketConnectionState {
    object Disconnected : WebSocketConnectionState()
    object Connecting : WebSocketConnectionState()
    object Connected : WebSocketConnectionState()
    data class Error(val throwable: Throwable) : WebSocketConnectionState()
}