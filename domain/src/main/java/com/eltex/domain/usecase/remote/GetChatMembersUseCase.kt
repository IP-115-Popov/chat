package com.eltex.domain.usecase.remote

import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.remote.ChatMembersRemoteRepository

class GetChatMembersUseCase(
    private val chatMembersRemoteRepository: ChatMembersRemoteRepository
) {
    suspend operator fun invoke(chatModel: ChatModel) = chatMembersRemoteRepository.get(chatModel)
}