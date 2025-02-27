package com.eltex.domain.usecase.local

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.FileLocalRepository

class ClearCacheUseCase(
    private val authDataLocalRepository: AuthDataLocalRepository,
    private val fileLocalRepository: FileLocalRepository,
) {
    suspend operator fun invoke() {
        authDataLocalRepository.deleteAuthData()
        fileLocalRepository.deleteAllFiles()
    }
}