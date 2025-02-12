package com.eltex.data.di

import com.eltex.data.repository.ChatRepositoryImpl
import com.eltex.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatRepositoryModule {
    @Binds
    fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository
}