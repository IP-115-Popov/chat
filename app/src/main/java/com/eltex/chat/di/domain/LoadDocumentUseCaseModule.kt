package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.repository.remote.ImageRemoteRepository
import com.eltex.domain.usecase.remote.LoadDocumentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoadDocumentUseCaseModule {
    @Provides
    fun provideLoadDocumentUseCase(
        imageRemoteRepository: ImageRemoteRepository,
        fileLocalRepository: FileLocalRepository,
    ): LoadDocumentUseCase {
        return LoadDocumentUseCase(
            imageRemoteRepository = imageRemoteRepository,
            fileLocalRepository = fileLocalRepository,
        )
    }
}