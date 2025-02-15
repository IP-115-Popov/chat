package com.eltex.domain.usecase

import com.eltex.domain.models.Message
import com.eltex.domain.repository.MessageHistoryRepository

class GetHistoryChatUseCase(
    private val messageHistoryRepository: MessageHistoryRepository
) {
    suspend fun execute(
        roomId: String, roomType: String, offset: Int, count: Int
    ): List<Message> = messageHistoryRepository.getMessageHistory(
        roomId = roomId, roomType = roomType, offset = offset, count = count
    )
}