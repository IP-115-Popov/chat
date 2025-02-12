package com.eltex.domain.usecase

import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.websocket.WebSocketConnectionState
import com.eltex.domain.websocket.WebSocketManager
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