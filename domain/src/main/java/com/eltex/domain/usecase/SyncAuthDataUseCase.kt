package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.TokenRepository
import com.eltex.domain.models.AuthData

class SyncAuthDataUseCase(
    private val authDataRepository: AuthDataRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend fun execute(): Either<String, AuthData> {
        val authData = authDataRepository.getAuthData()
        return if (authData != null) {
            tokenRepository.setToken(authData.authToken)
            authData.right()
        } else {
            "Error".left()
        }
    }
}