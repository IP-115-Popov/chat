package com.eltex.chat.feature.authorization.di

import com.eltex.chat.data.repository.TokenRepositoryImpl
import com.eltex.chat.feature.authorization.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TokenRepositoryModule {
    @Binds
    fun bindsTokenRepository(impl: TokenRepositoryImpl): TokenRepository
}