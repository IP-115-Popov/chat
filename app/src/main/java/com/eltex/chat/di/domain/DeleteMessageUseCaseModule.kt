package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.usecase.remote.DeleteMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DeleteMessageUseCaseModule {
    @Provides
    fun provideDeleteMessageUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository
    ): DeleteMessageUseCase {
        return DeleteMessageUseCase(
            chatMessageRemoteRepository = chatMessageRemoteRepository,
        )
    }
}