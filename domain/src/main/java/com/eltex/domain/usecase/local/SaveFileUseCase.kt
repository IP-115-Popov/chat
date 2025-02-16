package com.eltex.domain.usecase.local

import com.eltex.domain.repository.FileLocalRepository

class SaveFileUseCase(
    private val fileLocalRepository: FileLocalRepository,
) {
    suspend fun execute(uri: String, data: ByteArray) =
        fileLocalRepository.saveFileData(uri = uri, data = data)
}