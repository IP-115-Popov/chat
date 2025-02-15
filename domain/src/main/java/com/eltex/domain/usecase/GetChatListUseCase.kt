package com.eltex.domain.usecase

import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.ChatRemoteRepository
import kotlinx.coroutines.flow.Flow

class GetChatListUseCase(
    private val chatRemoteRepository: ChatRemoteRepository
) {
    suspend fun execute(): Flow<List<ChatModel>> = chatRemoteRepository.getChat()
}