package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.HeaderRepository

class SyncAuthDataUseCase(
    private val authDataRepository: AuthDataRepository,
    private val headerRepository: HeaderRepository,
) {
    suspend fun execute(): Either<String, AuthData> {
        val authData = authDataRepository.getAuthData()
        return if (authData != null) {
            headerRepository.setToken(authData.authToken)
            headerRepository.setUserID(authData.userId)
            authData.right()
        } else {
            "Error".left()
        }
    }
}