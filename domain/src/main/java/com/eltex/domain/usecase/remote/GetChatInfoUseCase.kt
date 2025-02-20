package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ChatRemoteRepository

class GetChatInfoUseCase(
    private val chatRemoteRepository: ChatRemoteRepository
) {
    suspend operator fun invoke(roomId: String) = chatRemoteRepository.getChatInfo(roomId = roomId)
}