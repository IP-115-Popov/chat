package com.eltex.domain.usecase.remote

import com.eltex.domain.models.ChatModel
import com.eltex.domain.repository.remote.AvatarRemoteRepository

class GetRoomAvatarUseCase(
    private val avatarRemoteRepository: AvatarRemoteRepository,
) {
    suspend operator fun invoke(chat: ChatModel, username: String?): ByteArray? {
        if (chat.t == "d") {
            chat.usernames?.firstOrNull { it != username }?.let { username ->
                avatarRemoteRepository.getAvatar(
                    subject = username
                ).onRight { avatarRes ->
                    return avatarRes
                }
            }

            return null
        } else {
            avatarRemoteRepository.getRoomAvatar(roomId = chat.id).onRight { avatarRes ->
                return avatarRes
            }
            return null
        }
    }
}