package com.eltex.domain.feature.profile.repository

import arrow.core.Either
import com.eltex.domain.models.DataError

interface ImageRepository {
    suspend fun getImage(imageUrl: String): Either<DataError, ByteArray>
}