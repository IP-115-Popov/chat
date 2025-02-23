package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ChatMembersRemoteRepositoryImpl
import com.eltex.domain.repository.remote.ChatMembersRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ChatMembersRemoteRepositoryModule {
    @Binds
    fun bindChatMembersRemoteRepository(impl: ChatMembersRemoteRepositoryImpl): ChatMembersRemoteRepository
}