package com.eltex.domain.repository

import com.eltex.domain.models.ChatModel
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChat(): Flow<List<ChatModel>>
    suspend fun createChat(chatName: String, userNameList: List<String>): Flow<List<ChatModel>>
}