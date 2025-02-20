package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatRemoteRepository
import com.eltex.domain.usecase.remote.GetChatInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetChatInfoUseCaseModule {
    @Provides
    fun provideGetChatInfoUseCase(
        chatRemoteRepository: ChatRemoteRepository,
    ): GetChatInfoUseCase {
        return GetChatInfoUseCase(
            chatRemoteRepository = chatRemoteRepository,
        )
    }
}