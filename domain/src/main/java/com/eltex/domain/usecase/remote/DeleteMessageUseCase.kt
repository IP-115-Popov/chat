package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository

class DeleteMessageUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend operator fun invoke(roomId: String, msgId: String) =
        chatMessageRemoteRepository.deleteMessages(roomId = roomId, msgId = msgId)
}