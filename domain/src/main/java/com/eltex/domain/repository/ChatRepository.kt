package com.eltex.domain.repository

import com.eltex.domain.models.ChatModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChat(): Flow<ChatModel>
}