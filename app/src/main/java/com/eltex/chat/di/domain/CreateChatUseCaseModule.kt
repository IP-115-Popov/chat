package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatCreationRemoteRepository
import com.eltex.domain.usecase.remote.CreateChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CreateChatUseCaseModule {
    @Provides
    fun provideCreateChatUseCase(
        chatCreationRemoteRepository: ChatCreationRemoteRepository,
    ): CreateChatUseCase {
        return CreateChatUseCase(
            chatCreationRemoteRepository = chatCreationRemoteRepository,
        )
    }
}