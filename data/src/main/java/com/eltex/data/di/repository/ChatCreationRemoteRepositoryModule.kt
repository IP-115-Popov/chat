package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ChatCreationNetworkRepositoryImpl
import com.eltex.domain.repository.remote.ChatCreationRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatCreationRemoteRepositoryModule {
    @Binds
    fun bindChatCreationRemoteRepository(impl: ChatCreationNetworkRepositoryImpl): ChatCreationRemoteRepository
}