package com.eltex.domain.feature.profile.usecase

import arrow.core.Either
import com.eltex.domain.feature.profile.repository.ImageLocalRepository
import com.eltex.domain.feature.profile.repository.ImageNetworkRepository
import com.eltex.domain.models.DataError

class GetImageUseCase(
    private val imageNetworkRepository: ImageNetworkRepository,
    private val imageLocalRepository: ImageLocalRepository
) {
    suspend fun execute(imageUrl: String): Either<DataError, ByteArray> {
        val getImgResult = imageNetworkRepository.getImageByteArray(imageUrl)
        getImgResult.isRight { img: ByteArray ->
            imageLocalRepository.saveImageData(imageUrl = imageUrl, data = img)
            return getImgResult
        }

        getImgResult.isLeft {
            return imageLocalRepository.getImageData(imageUrl)
        }

        return getImgResult
    }
}