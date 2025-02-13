package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatCreationNetworkRepository

class CreateChatUseCase(
    private val chatCreationNetworkRepository: ChatCreationNetworkRepository
) {
    suspend fun execute(
        xAuthToken: String,
        userId: String,
        username: String,
    ) {
        chatCreationNetworkRepository.createChat(
            xAuthToken = xAuthToken,
            userId = userId,
            userName = username
        )
    }
}