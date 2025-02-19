package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.ChatCreationRemoteRepository

class CreateChatUseCase(
    private val chatCreationRemoteRepository: ChatCreationRemoteRepository
) {
    suspend operator fun invoke(
        username: String,
    ): Either<DataError, ChatModel> =
        chatCreationRemoteRepository.createChat(
            userName = username
        )

}