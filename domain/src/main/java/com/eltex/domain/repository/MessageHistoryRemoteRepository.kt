package com.eltex.domain.repository

import com.eltex.domain.models.Message

interface MessageHistoryRemoteRepository {
    suspend fun getMessageHistory(
        roomId: String,
        roomType: String,
        offset: Int,
        count: Int
    ): List<Message>
}