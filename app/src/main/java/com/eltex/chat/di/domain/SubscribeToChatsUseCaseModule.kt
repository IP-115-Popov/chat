package com.eltex.chat.di.domain

import com.eltex.domain.HeaderManager
import com.eltex.domain.repository.remote.ChatRemoteRepository
import com.eltex.domain.usecase.remote.SubscribeToChatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SubscribeToChatsUseCaseModule {
    @Provides
    fun provideSubscribeToChatsUseCase(
        chatRemoteRepository: ChatRemoteRepository,
        headerManager: HeaderManager,
    ): SubscribeToChatsUseCase {
        return SubscribeToChatsUseCase(
            chatRemoteRepository = chatRemoteRepository,
            headerManager = headerManager,
        )
    }
}