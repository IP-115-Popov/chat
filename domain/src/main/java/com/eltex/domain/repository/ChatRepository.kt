package com.eltex.domain.repository

import kotlinx.coroutines.flow.Flow

data class ChatDTO(val id: String, val name: String, val lastMessage: String)

interface ChatRepository {
    suspend fun getChat(): Flow<ChatDTO>
}