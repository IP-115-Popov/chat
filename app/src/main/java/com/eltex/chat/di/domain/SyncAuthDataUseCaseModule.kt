package com.eltex.chat.di.domain

import com.eltex.domain.HeaderManager
import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SyncAuthDataUseCaseModule {
    @Provides
    fun provideSyncAuthDataUseCase(
        authDataLocalRepository: AuthDataLocalRepository,
        headerManager: HeaderManager,
    ): SyncAuthDataUseCase {
        return SyncAuthDataUseCase(
            authDataLocalRepository = authDataLocalRepository,
            headerManager = headerManager,
        )
    }
}