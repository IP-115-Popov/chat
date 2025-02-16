package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository

class GetMessageFromChatUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend fun execute(roomId: String) =
        chatMessageRemoteRepository.subscribeToRoomMessages(roomId = roomId)
}