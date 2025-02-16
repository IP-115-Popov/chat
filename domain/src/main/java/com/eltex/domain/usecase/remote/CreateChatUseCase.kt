package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ChatCreationRemoteRepository

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