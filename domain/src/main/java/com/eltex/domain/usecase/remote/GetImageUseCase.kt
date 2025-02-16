package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.FileLocalRepository
import com.eltex.domain.repository.ImageRemoteRepository

class GetImageUseCase(
    private val imageRemoteRepository: ImageRemoteRepository,
    private val fileLocalRepository: FileLocalRepository
) {
    suspend fun execute(imageUrl: String): Either<DataError, ByteArray> {
        val getImgResult = imageRemoteRepository.getImageByteArray(imageUrl)
//        getImgResult.onRight { img: ByteArray ->
//            fileLocalRepository.saveFileData(uri = imageUrl, data = img)
//            return getImgResult
//        }
//
//        getImgResult.isLeft {
//            return fileLocalRepository.getFileData(imageUrl)
//        }

        return getImgResult
    }
}
