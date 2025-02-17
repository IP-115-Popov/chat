package com.eltex.domain.usecase.remote

import com.eltex.domain.models.MessagePayload
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository

class SendMessageUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend operator fun invoke(messagePayload: MessagePayload) =
        chatMessageRemoteRepository.sendMessages(messagePayload = messagePayload)
}