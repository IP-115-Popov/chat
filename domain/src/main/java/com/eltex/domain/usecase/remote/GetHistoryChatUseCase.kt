package com.eltex.domain.usecase.remote

import com.eltex.domain.models.Message
import com.eltex.domain.repository.remote.MessageHistoryRemoteRepository

class GetHistoryChatUseCase(
    private val messageHistoryRemoteRepository: MessageHistoryRemoteRepository
) {
    suspend fun execute(
        roomId: String, roomType: String, offset: Int, count: Int
    ): List<Message> = messageHistoryRemoteRepository.getMessageHistory(
        roomId = roomId, roomType = roomType, offset = offset, count = count
    )
}