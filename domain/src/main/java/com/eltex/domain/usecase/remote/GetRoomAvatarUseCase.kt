package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.AvatarRemoteRepository

class GetRoomAvatarUseCase(
    private val avatarRemoteRepository: AvatarRemoteRepository
) {
    suspend operator fun invoke(roomId: String) =
        avatarRemoteRepository.getRoomAvatar(roomId = roomId)
}