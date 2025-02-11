package com.eltex.domain.feature.profile.usecase

import arrow.core.Either
import com.eltex.domain.feature.profile.repository.ImageRepository
import com.eltex.domain.models.DataError

class GetImageUseCase(private val imageRepository: ImageRepository) {
    suspend fun execute(imageUrl: String): Either<DataError, ByteArray> {
        return imageRepository.getImage(imageUrl)
    }
}