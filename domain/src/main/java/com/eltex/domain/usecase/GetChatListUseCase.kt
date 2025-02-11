package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatDTO
import com.eltex.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatListUseCase(
    private val chatRepository: ChatRepository
) {
    suspend fun execute(): Flow<ChatDTO> = chatRepository.getChat()
}