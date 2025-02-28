package com.eltex.domain.repository.remote

import com.eltex.domain.models.Message
import com.eltex.domain.models.MessagePayload
import kotlinx.coroutines.flow.Flow

interface ChatMessageRemoteRepository {
    suspend fun subscribeToRoomMessages(roomId: String): Flow<Message>
    suspend fun subscribeToRoomDeleteMessagesId(roomId: String): Flow<String>
    suspend fun sendMessages(messagePayload: MessagePayload)
}