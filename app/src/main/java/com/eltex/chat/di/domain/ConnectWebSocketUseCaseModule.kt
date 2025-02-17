package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.websocket.WebSocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ConnectWebSocketUseCaseModule {
    @Provides
    fun provideConnectWebSocketUseCase(
        webSocketManager: WebSocketManager,
        authDataLocalRepository: AuthDataLocalRepository,
    ): ConnectWebSocketUseCase {
        return ConnectWebSocketUseCase(
            webSocketManager = webSocketManager,
            authDataLocalRepository = authDataLocalRepository,
        )
    }
}