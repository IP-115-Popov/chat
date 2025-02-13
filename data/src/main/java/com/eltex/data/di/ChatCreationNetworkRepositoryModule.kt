package com.eltex.data.di

import com.eltex.data.repository.ChatCreationNetworkRepositoryImpl
import com.eltex.domain.repository.ChatCreationNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatCreationNetworkRepositoryModule {
    @Binds
    fun bindChatCreationNetworkRepository(impl: ChatCreationNetworkRepositoryImpl): ChatCreationNetworkRepository
}