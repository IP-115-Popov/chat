package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.AvatarRemoteRepository

class GetAvatarUseCase(
    private val avatarRemoteRepository: AvatarRemoteRepository
) {
    suspend operator fun invoke(subject: String, rc_uid: String, rc_token: String) = avatarRemoteRepository.getAvatar(subject = subject, rc_uid = rc_uid, rc_token = rc_token)
}