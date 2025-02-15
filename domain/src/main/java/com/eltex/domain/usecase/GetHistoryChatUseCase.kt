package com.eltex.domain.usecase

import com.eltex.domain.models.Message
import com.eltex.domain.repository.MessageHistoryRemoteRepository

class GetHistoryChatUseCase(
    private val messageHistoryRemoteRepository: MessageHistoryRemoteRepository
) {
    suspend fun execute(
        roomId: String, roomType: String, offset: Int, count: Int
    ): List<Message> = messageHistoryRemoteRepository.getMessageHistory(
        roomId = roomId, roomType = roomType, offset = offset, count = count
    )
}