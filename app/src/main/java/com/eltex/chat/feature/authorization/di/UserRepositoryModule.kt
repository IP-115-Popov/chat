package com.eltex.chat.feature.authorization.di

import com.eltex.chat.data.repository.AuthDataRepositoryImpl
import com.eltex.chat.feature.authorization.repository.AuthDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthDataRepositoryModule {
    @Binds
    fun bindsAuthDataRepository(impl: AuthDataRepositoryImpl): AuthDataRepository
}