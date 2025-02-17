package com.eltex.domain.usecase.local

import com.eltex.domain.repository.local.FileLocalRepository

class LoadFromCacheFileUseCase(
    private val fileLocalRepository: FileLocalRepository,
) {
    suspend operator fun invoke(uri: String) = fileLocalRepository.getFileData(uri = uri)
}