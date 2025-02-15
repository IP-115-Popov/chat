package com.eltex.domain.usecase

import com.eltex.domain.repository.ChatMessageRemoteRepository

class GetMessageFromChatUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend fun execute(roomId: String) =
        chatMessageRemoteRepository.subscribeToRoomMessages(roomId = roomId)
}