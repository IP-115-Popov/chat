package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.DataError

interface ImageRemoteRepository {
    suspend fun getImageByteArray(url: String): Either<DataError, ByteArray>
}