package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError

interface ChatCreationRemoteRepository {
    suspend fun createChat(
        userName: String
    ): Either<DataError, ChatModel>
}