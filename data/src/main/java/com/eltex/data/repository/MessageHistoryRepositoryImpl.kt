package com.eltex.data.repository

import com.eltex.domain.models.Message
import com.eltex.domain.repository.MessageHistoryRepository

class MessageHistoryRepositoryImpl : MessageHistoryRepository {
    override suspend fun getMessageHistory(roomId: String, offset: Int, count: Int): List<Message> {
        TODO("Not yet implemented")
    }
}