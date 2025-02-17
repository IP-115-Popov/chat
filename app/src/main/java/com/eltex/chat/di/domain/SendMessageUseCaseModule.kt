package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.repository.remote.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SendMessageUseCaseModule {
    @Provides
    fun provideSendMessageUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
    ): SendMessageUseCase {
        return SendMessageUseCase(
            chatMessageRemoteRepository = chatMessageRemoteRepository,
        )
    }
}