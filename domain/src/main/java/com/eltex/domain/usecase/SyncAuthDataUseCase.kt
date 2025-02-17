package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.HeaderManager

class SyncAuthDataUseCase(
    private val authDataLocalRepository: AuthDataLocalRepository,
    private val headerManager: HeaderManager,
) {
    suspend operator fun invoke(): Either<String, AuthData> {
        val authData = authDataLocalRepository.getAuthData()
        return if (authData != null) {
            headerManager.setToken(authData.authToken)
            headerManager.setUserID(authData.userId)
            authData.right()
        } else {
            "Error".left()
        }
    }
}