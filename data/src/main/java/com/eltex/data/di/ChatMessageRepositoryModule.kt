package com.eltex.data.di

import com.eltex.data.repository.ChatMessageRepositoryImpl
import com.eltex.domain.repository.ChatMessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatMessageRepositoryModule {
    @Binds
    fun bindChatMessageRepository(impl: ChatMessageRepositoryImpl): ChatMessageRepository
}