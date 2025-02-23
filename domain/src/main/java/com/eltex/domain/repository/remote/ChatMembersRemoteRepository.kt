package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.ChatsMember
import com.eltex.domain.models.DataError

interface ChatMembersRemoteRepository {
    suspend fun get(chatModel: ChatModel): Either<DataError, List<ChatsMember>>
}