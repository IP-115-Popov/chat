package com.eltex.domain.repository

import kotlinx.coroutines.flow.Flow

interface ChatMessageRepository {
    suspend fun subscribeToRoomMessages(roomId: String): Flow<String>
}