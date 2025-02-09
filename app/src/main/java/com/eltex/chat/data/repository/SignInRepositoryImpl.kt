package com.eltex.chat.data.repository

import com.eltex.chat.data.api.AuthorizationApi
import com.eltex.chat.data.models.LoginRequest
import com.eltex.chat.feature.authorization.models.LoginUiModel
import com.eltex.chat.feature.authorization.repository.SignInRepository
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val authorizationApi: AuthorizationApi
) : SignInRepository {
    override suspend fun signIn(loginUiRequest: LoginUiModel): String {
        val response =
            authorizationApi.signIn(loginRequest = LoginRequest.toLoginRequest(loginUiRequest))

        return if (response.isSuccessful) {
            val token = response.body()
            if (token != null) {
                token.data.authToken
            } else {
                ""
            }
        } else {
            ""
        }
    }
}