package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.AvatarRemoteRepository
import com.eltex.domain.usecase.remote.GetAvatarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetAvatarUseCaseModule {
    @Provides
    fun provideGetAvatarUseCase(
        avatarRemoteRepository: AvatarRemoteRepository
    ): GetAvatarUseCase {
        return GetAvatarUseCase(
            avatarRemoteRepository = avatarRemoteRepository,
        )
    }
}