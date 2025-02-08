package com.eltex.chat.feature.authorization.repository

import com.eltex.chat.feature.authorization.models.LoginUiModel

interface SignInRepository {
    suspend fun signIn(loginUiRequest: LoginUiModel): String
}