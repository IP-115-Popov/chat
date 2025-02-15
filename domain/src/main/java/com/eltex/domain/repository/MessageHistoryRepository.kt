package com.eltex.domain.repository

import com.eltex.domain.models.Message

interface MessageHistoryRepository {
    suspend fun getMessageHistory(roomId: String, roomType: String, offset: Int, count: Int): List<Message>
}