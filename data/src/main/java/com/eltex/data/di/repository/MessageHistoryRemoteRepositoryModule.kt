package com.eltex.data.di.repository

import com.eltex.data.repository.remote.MessageHistoryRemoteRepositoryImpl
import com.eltex.domain.repository.remote.MessageHistoryRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MessageHistoryRemoteRepositoryModule {
    @Binds
    fun bindMessageHistoryRemoteRepository(impl: MessageHistoryRemoteRepositoryImpl): MessageHistoryRemoteRepository
}