package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.MessageHistoryRemoteRepository
import com.eltex.domain.usecase.remote.GetHistoryChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetHistoryChatUseCaseModule {
    @Provides
    fun provideGetHistoryChatUseCase(
        messageHistoryRemoteRepository: MessageHistoryRemoteRepository,
    ): GetHistoryChatUseCase {
        return GetHistoryChatUseCase(
            messageHistoryRemoteRepository = messageHistoryRemoteRepository,
        )
    }
}