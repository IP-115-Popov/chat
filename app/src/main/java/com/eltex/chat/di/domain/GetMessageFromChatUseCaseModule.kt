package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetMessageFromChatUseCaseModule {
    @Provides
    fun provideGetMessageFromChatUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
    ): GetMessageFromChatUseCase {
        return GetMessageFromChatUseCase(
            chatMessageRemoteRepository = chatMessageRemoteRepository,
        )
    }
}