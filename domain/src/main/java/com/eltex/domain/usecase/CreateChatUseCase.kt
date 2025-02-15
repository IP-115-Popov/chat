package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatCreationRemoteRepository

class CreateChatUseCase(
    private val chatCreationRemoteRepository: ChatCreationRemoteRepository
) {
    suspend fun execute(
        xAuthToken: String,
        userId: String,
        username: String,
    ) {
        chatCreationRemoteRepository.createChat(
            xAuthToken = xAuthToken,
            userId = userId,
            userName = username
        )
    }
}