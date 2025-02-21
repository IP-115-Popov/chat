package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.DataError

interface AvatarRemoteRepository {
    suspend fun getAvatar(
        subject: String, rc_uid: String, rc_token: String
    ): Either<DataError, ByteArray>
}