package com.eltex.domain.repository

import arrow.core.Either
import com.eltex.domain.models.DataError

interface ImageLocalRepository {
    suspend fun getImageData(imageUrl: String): Either<DataError, ByteArray>
    suspend fun saveImageData(imageUrl: String, data: ByteArray)
}