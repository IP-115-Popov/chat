package com.eltex.domain.usecase.remote

import com.eltex.domain.HeaderManager
import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.remote.ChatRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SubscribeToChatsUseCase(
    private val chatRemoteRepository: ChatRemoteRepository,
    private val headerManager: HeaderManager,
) {
    suspend operator fun invoke(): Flow<ChatModel> = headerManager.getUserID()?.let { userId ->
        chatRemoteRepository.subscribeToChats(userId)
    } ?: run {
        emptyFlow()
    }
}