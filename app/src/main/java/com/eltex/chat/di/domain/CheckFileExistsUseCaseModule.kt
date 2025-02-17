package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.usecase.local.CheckFileExistsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CheckFileExistsUseCaseModule {
    @Provides
    fun provideCheckFileExistsUseCase(
        fileLocalRepository: FileLocalRepository,
    ): CheckFileExistsUseCase {
        return CheckFileExistsUseCase(
            fileLocalRepository = fileLocalRepository,
        )
    }
}