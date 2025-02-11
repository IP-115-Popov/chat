package com.eltex.domain.repository

import arrow.core.Either
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.LoginModel
import com.eltex.domain.models.SignInError

interface SignInNetworkRepository {
    suspend fun signIn(loginModel: LoginModel): Either<SignInError, AuthData>
}