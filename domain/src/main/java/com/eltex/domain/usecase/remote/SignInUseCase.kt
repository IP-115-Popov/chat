package com.eltex.domain.usecase.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.LoginModel
import com.eltex.domain.models.SignInError
import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.HeaderLocalRepository
import com.eltex.domain.repository.remote.SignInRemoteRepository

class SignInUseCase(
    private val signInRemoteRepository: SignInRemoteRepository,
    private val headerLocalRepository: HeaderLocalRepository,
    private val authDataLocalRepository: AuthDataLocalRepository,
) {
    suspend operator fun invoke(loginModel: LoginModel): Either<SignInError, AuthData> {
        val result = signInRemoteRepository.signIn(loginModel)
        return when (result) {
            is Either.Left -> {
                result.value.left()
            }

            is Either.Right -> {
                authDataLocalRepository.saveAuthData(result.value)
                headerLocalRepository.setToken(result.value.authToken)
                result.value.right()
            }
        }
    }
}