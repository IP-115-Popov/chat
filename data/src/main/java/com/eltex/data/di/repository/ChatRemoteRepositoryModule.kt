package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ChatWebSocketRepositoryImpl
import com.eltex.domain.repository.remote.ChatRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatRemoteRepositoryModule {
    @Binds
    fun bindChatRemoteRepository(impl: ChatWebSocketRepositoryImpl): ChatRemoteRepository
}