package com.eltex.domain.repository

import arrow.core.Either
import com.eltex.domain.models.DataError

interface ImageNetworkRepository {
    suspend fun getImageByteArray(url: String): Either<DataError, ByteArray>
}