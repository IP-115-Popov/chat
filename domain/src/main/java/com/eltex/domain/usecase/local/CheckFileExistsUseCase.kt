package com.eltex.domain.usecase.local

import com.eltex.domain.repository.local.FileLocalRepository

class CheckFileExistsUseCase(
    private val fileLocalRepository: FileLocalRepository
) {
    suspend fun execute(uri: String): Boolean {
        return fileLocalRepository.getFileData(uri).isRight()
    }
}