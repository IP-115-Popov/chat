package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.repository.remote.ImageRemoteRepository

class GetImageUseCase(
    private val imageRemoteRepository: ImageRemoteRepository,
) {
    suspend operator fun invoke(imageUrl: String): Either<DataError, ByteArray> =
        imageRemoteRepository.getImageByteArray(imageUrl)
}
