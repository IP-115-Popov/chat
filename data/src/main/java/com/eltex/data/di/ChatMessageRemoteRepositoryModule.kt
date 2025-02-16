package com.eltex.data.di

import com.eltex.data.repository.remote.ChatMessageRemoteRepositoryImpl
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatMessageRemoteRepositoryModule {
    @Binds
    fun bindChatMessageRemoteRepository(impl: ChatMessageRemoteRepositoryImpl): ChatMessageRemoteRepository
}