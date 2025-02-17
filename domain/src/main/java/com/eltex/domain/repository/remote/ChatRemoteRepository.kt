package com.eltex.domain.repository.remote

import com.eltex.domain.models.ChatModel
import kotlinx.coroutines.flow.Flow

interface ChatRemoteRepository {
    suspend fun getChat(): Flow<List<ChatModel>>
}