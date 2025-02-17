package com.eltex.domain.usecase.remote

import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.repository.remote.ImageRemoteRepository

class LoadDocumentUseCase(
    private val imageRemoteRepository: ImageRemoteRepository,
    private val fileLocalRepository: FileLocalRepository,
) {
    suspend operator fun invoke(uri: String) {
        imageRemoteRepository.getImageByteArray(uri).onRight { document ->
            try {
                fileLocalRepository.saveFileData(uri = uri, data = document)
            } catch (e: Exception) {
                println("LoadDocumentUseCase ${e.message}")
            }
        }
    }
}