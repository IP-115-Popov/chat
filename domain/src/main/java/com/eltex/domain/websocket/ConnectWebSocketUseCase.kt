package com.eltex.domain.websocket

import kotlinx.coroutines.flow.Flow

class ConnectWebSocketUseCase(
    private val webSocketManager: WebSocketManager
) {
    fun execute(): Flow<WebSocketConnectionState> {
        webSocketManager.connect()
        return webSocketManager.connectionState
    }
}