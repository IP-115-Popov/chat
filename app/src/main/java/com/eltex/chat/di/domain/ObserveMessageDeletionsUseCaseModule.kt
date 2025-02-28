package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.usecase.remote.ObserveMessageDeletionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ObserveMessageDeletionsUseCaseModule {
    @Provides
    fun provideObserveMessageDeletionsUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository
    ): ObserveMessageDeletionsUseCase {
        return ObserveMessageDeletionsUseCase(
            chatMessageRemoteRepository = chatMessageRemoteRepository,
        )
    }
}