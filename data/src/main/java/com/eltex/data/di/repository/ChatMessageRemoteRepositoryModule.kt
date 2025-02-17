package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ChatMessageWebSocketRepositoryImpl
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatMessageRemoteRepositoryModule {
    @Binds
    fun bindChatMessageRemoteRepository(impl: ChatMessageWebSocketRepositoryImpl): ChatMessageRemoteRepository
}