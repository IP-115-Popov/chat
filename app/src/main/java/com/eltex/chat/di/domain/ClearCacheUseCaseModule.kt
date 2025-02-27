package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.usecase.local.ClearCacheUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ClearCacheUseCaseModule {
    @Provides
    fun provideClearCacheUseCase(
        authDataLocalRepository: AuthDataLocalRepository,
        fileLocalRepository: FileLocalRepository,
    ): ClearCacheUseCase {
        return ClearCacheUseCase(
            authDataLocalRepository = authDataLocalRepository,
            fileLocalRepository = fileLocalRepository,
        )
    }
}