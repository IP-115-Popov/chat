package com.eltex.domain.websocket

import com.eltex.domain.repository.AuthDataRepository
import kotlinx.coroutines.flow.Flow

class ConnectWebSocketUseCase(
    private val webSocketManager: WebSocketManager,
    private val authDataRepository: AuthDataRepository,
) {
    suspend fun execute(): Flow<WebSocketConnectionState> {
        val authData = authDataRepository.getAuthData()
        authData?.let {
            webSocketManager.connect(authData.authToken)
        }
        return webSocketManager.connectionState
    }
}