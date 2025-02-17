package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ImageRemoteRepository
import com.eltex.domain.usecase.remote.GetImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetImageUseCaseModule {
    @Provides
    fun provideGetImageUseCase(
        imageRemoteRepository: ImageRemoteRepository,
    ): GetImageUseCase {
        return GetImageUseCase(
            imageRemoteRepository = imageRemoteRepository,
        )
    }
}