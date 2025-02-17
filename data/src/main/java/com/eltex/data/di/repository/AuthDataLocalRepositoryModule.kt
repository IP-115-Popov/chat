package com.eltex.data.di.repository

import com.eltex.data.repository.local.AuthDataSharedPrefRepositoryImpl
import com.eltex.domain.repository.local.AuthDataLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthDataLocalRepositoryModule {
    @Binds
    fun bindsAuthDataLocalRepository(impl: AuthDataSharedPrefRepositoryImpl): AuthDataLocalRepository
}