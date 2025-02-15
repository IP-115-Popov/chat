package com.eltex.domain.usecase

import com.eltex.domain.repository.AuthDataLocalRepository
import com.eltex.domain.websocket.WebSocketConnectionState
import com.eltex.domain.websocket.WebSocketManager
import kotlinx.coroutines.flow.Flow

class ConnectWebSocketUseCase(
    private val webSocketManager: WebSocketManager,
    private val authDataLocalRepository: AuthDataLocalRepository,
) {
    suspend fun execute(): Flow<WebSocketConnectionState> {
        val authData = authDataLocalRepository.getAuthData()
        authData?.let {
            webSocketManager.connect(authData.authToken)
        }
        return webSocketManager.connectionState
    }
}