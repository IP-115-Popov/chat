package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatMessageRepository

class GetMessageFromChatUseCase(
    private val chatMessageRepository: ChatMessageRepository
) {
    suspend fun execute(roomId: String) =
        chatMessageRepository.subscribeToRoomMessages(roomId = roomId)
}