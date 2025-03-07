package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.DataError

interface AvatarRemoteRepository {
    suspend fun getAvatar(
        subject: String
    ): Either<DataError, ByteArray>

    suspend fun getRoomAvatar(
        roomId: String
    ): Either<DataError, ByteArray>
}