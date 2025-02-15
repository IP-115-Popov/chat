package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatCreationRemoteRepository

class CreateChatUseCase(
    private val chatCreationRemoteRepository: ChatCreationRemoteRepository
) {
    suspend fun execute(
        username: String,
    ) {
        chatCreationRemoteRepository.createChat(
            userName = username
        )
    }
}