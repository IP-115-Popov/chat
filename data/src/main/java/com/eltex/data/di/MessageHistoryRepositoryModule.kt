package com.eltex.data.di

import com.eltex.data.repository.MessageHistoryRepositoryImpl
import com.eltex.domain.repository.MessageHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MessageHistoryRepositoryModule {
    @Binds
    fun bindMessageHistoryRepository(impl: MessageHistoryRepositoryImpl): MessageHistoryRepository
}