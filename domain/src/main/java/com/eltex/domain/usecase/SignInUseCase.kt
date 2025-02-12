package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.LoginModel
import com.eltex.domain.models.SignInError
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.SignInNetworkRepository
import com.eltex.domain.repository.TokenRepository

class SignInUseCase(
    private val signInNetworkRepository: SignInNetworkRepository,
    private val tokenRepository: TokenRepository,
    private val authDataRepository: AuthDataRepository,
) {
    suspend fun execute(loginModel: LoginModel): Either<SignInError, AuthData> {
        val result = signInNetworkRepository.signIn(loginModel)
        return when (result) {
            is Either.Left -> {
                result.value.left()
            }

            is Either.Right -> {
                authDataRepository.saveAuthData(result.value)
                tokenRepository.setToken(result.value.authToken)
                result.value.right()
            }
        }
    }
}