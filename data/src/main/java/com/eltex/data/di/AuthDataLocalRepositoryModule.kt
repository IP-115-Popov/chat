package com.eltex.data.di

import com.eltex.data.repository.local.AuthDataLocalRepositoryImpl
import com.eltex.domain.repository.local.AuthDataLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthDataLocalRepositoryModule {
    @Binds
    fun bindsAuthDataLocalRepository(impl: AuthDataLocalRepositoryImpl): AuthDataLocalRepository
}