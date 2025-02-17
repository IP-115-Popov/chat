package com.eltex.domain.repository.remote

import com.eltex.domain.models.MessagePayload

class SendMessageUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend operator fun invoke(messagePayload: MessagePayload) =
        chatMessageRemoteRepository.sendMessages(messagePayload = messagePayload)
}