package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatMembersRemoteRepository
import com.eltex.domain.usecase.remote.GetChatMembersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetChatMembersUseCaseModule {
    @Provides
    fun provideGetChatMembersUseCase(
        chatMembersRemoteRepository: ChatMembersRemoteRepository
    ): GetChatMembersUseCase {
        return GetChatMembersUseCase(
            chatMembersRemoteRepository = chatMembersRemoteRepository
        )
    }
}