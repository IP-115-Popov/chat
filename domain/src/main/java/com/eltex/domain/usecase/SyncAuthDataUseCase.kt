package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.HeaderLocalRepository

class SyncAuthDataUseCase(
    private val authDataLocalRepository: AuthDataLocalRepository,
    private val headerLocalRepository: HeaderLocalRepository,
) {
    suspend fun execute(): Either<String, AuthData> {
        val authData = authDataLocalRepository.getAuthData()
        return if (authData != null) {
            headerLocalRepository.setToken(authData.authToken)
            headerLocalRepository.setUserID(authData.userId)
            authData.right()
        } else {
            "Error".left()
        }
    }
}