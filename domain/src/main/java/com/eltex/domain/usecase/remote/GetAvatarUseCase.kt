package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.AvatarRemoteRepository

class GetAvatarUseCase(
    private val avatarRemoteRepository: AvatarRemoteRepository
) {
    suspend operator fun invoke(subject: String) = avatarRemoteRepository.getAvatar(subject = subject)
}