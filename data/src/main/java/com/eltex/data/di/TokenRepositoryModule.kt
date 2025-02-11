package com.eltex.data.di

import com.eltex.data.repository.TokenRepositoryImpl
import com.eltex.domain.repository.TokenRepository
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