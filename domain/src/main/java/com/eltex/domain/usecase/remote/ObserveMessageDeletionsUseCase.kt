package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ChatMessageRemoteRepository

class ObserveMessageDeletionsUseCase(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository
) {
    suspend operator fun invoke(roomId: String) =
        chatMessageRemoteRepository.subscribeToRoomDeleteMessagesId(roomId = roomId)
}