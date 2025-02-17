package com.eltex.data.di.repository

import com.eltex.data.websockets.WebSocketManagerImpl
import com.eltex.domain.websocket.WebSocketManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WebSocketManagerModule {
    @Binds
    fun bindWebSocketManager(impl: WebSocketManagerImpl): WebSocketManager
}