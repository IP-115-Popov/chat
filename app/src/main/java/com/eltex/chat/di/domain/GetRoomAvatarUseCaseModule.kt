package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.AvatarRemoteRepository
import com.eltex.domain.usecase.remote.GetRoomAvatarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetRoomAvatarUseCaseModule {
    @Provides
    fun provideGetRoomAvatarUseCase(
        avatarRemoteRepository: AvatarRemoteRepository
    ): GetRoomAvatarUseCase {
        return GetRoomAvatarUseCase(
            avatarRemoteRepository = avatarRemoteRepository,
        )
    }
}