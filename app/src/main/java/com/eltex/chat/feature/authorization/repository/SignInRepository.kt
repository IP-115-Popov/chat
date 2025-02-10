package com.eltex.chat.feature.authorization.repository

import arrow.core.Either
import com.eltex.chat.feature.authorization.models.LoginUiModel
import com.eltex.chat.feature.authorization.models.SignInError
import com.eltex.chat.feature.authorization.models.AuthData

interface SignInRepository {
    suspend fun signIn(loginUiRequest: LoginUiModel): Either<SignInError, AuthData>
}