package com.eltex.domain.repository

import com.eltex.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatMessageRepository {
    suspend fun subscribeToRoomMessages(roomId: String): Flow<Message>
}