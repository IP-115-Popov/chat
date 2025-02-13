package com.eltex.domain.usecase

import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class CreateChatUseCase(
    private val chatRepository: ChatRepository
) {
    suspend fun execute(
        chatName: String,
    ): Flow<List<ChatModel>> {

        val a = chatRepository.createChat(chatName = chatName)
        return  a
    }
}