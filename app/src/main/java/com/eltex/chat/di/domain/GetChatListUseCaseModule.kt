package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatRemoteRepository
import com.eltex.domain.usecase.remote.GetChatListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetChatListUseCaseModule {
    @Provides
    fun provideGetChatListUseCase(
        chatRemoteRepository: ChatRemoteRepository,
    ): GetChatListUseCase {
        return GetChatListUseCase(
            chatRemoteRepository = chatRemoteRepository
        )
    }
}