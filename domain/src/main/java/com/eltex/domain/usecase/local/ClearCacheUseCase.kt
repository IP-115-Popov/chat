package com.eltex.domain.usecase.local

import com.eltex.domain.repository.local.AuthDataLocalRepository

class ClearCacheUseCase(
    private val authDataLocalRepository: AuthDataLocalRepository
) {
    suspend operator fun invoke() {
        authDataLocalRepository.deleteAuthData()
    }
}