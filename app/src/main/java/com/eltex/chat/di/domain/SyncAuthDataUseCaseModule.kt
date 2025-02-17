package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.HeaderLocalRepository
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SyncAuthDataUseCaseModule {
    @Provides
    fun provideSyncAuthDataUseCase(
        authDataLocalRepository: AuthDataLocalRepository,
        headerLocalRepository: HeaderLocalRepository,
    ): SyncAuthDataUseCase {
        return SyncAuthDataUseCase(
            authDataLocalRepository = authDataLocalRepository,
            headerLocalRepository = headerLocalRepository,
        )
    }
}