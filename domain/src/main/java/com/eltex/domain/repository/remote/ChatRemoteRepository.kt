package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError
import kotlinx.coroutines.flow.Flow

interface ChatRemoteRepository {
    suspend fun getChats(): Flow<List<ChatModel>>
    suspend fun getChatInfo(roomId: String): Either<DataError, ChatModel>
    suspend fun subscribeToChats(userId: String): Flow<ChatModel>
}