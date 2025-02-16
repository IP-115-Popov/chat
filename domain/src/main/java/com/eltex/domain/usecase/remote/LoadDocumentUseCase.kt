package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.remote.ImageRemoteRepository

class LoadDocumentUseCase(
    private val imageRemoteRepository: ImageRemoteRepository,
) {
    suspend fun execute(uri: String) = imageRemoteRepository.getImageByteArray(uri)
}