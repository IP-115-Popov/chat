package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.usecase.local.LoadFromCacheFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoadFromCacheFileUseCaseModule {
    @Provides
    fun provideLoadFromCacheFileUseCase(
        fileLocalRepository: FileLocalRepository,
    ): LoadFromCacheFileUseCase {
        return LoadFromCacheFileUseCase(
            fileLocalRepository = fileLocalRepository,
        )
    }
}