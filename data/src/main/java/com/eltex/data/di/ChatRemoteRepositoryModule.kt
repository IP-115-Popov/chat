package com.eltex.data.di

import com.eltex.data.repository.ChatRemoteRepositoryImpl
import com.eltex.domain.repository.ChatRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatRemoteRepositoryModule {
    @Binds
    fun bindChatRemoteRepository(impl: ChatRemoteRepositoryImpl): ChatRemoteRepository
}