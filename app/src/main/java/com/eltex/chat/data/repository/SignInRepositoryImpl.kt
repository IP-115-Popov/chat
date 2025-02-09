package com.eltex.chat.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.chat.data.api.AuthorizationApi
import com.eltex.chat.data.mappers.LoginResponseToAuthDataMapper
import com.eltex.chat.data.models.LoginRequest
import com.eltex.chat.feature.authorization.models.LoginUiModel
import com.eltex.chat.feature.authorization.models.SignInError
import com.eltex.chat.feature.authorization.repository.SignInRepository
import com.eltex.chat.models.AuthData
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val authorizationApi: AuthorizationApi
) : SignInRepository {
    override suspend fun signIn(loginUiRequest: LoginUiModel): Either<SignInError, AuthData> {
        val response =
            authorizationApi.signIn(loginRequest = LoginRequest.toLoginRequest(loginUiRequest))

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                LoginResponseToAuthDataMapper.map(loginResponse).right()
            } else {
                when (response.code()) {
                    401 -> SignInError.Unauthorized.left()
                    else -> SignInError.ConnectionMissing.left()
                }
            }
        } else {
            when (response.code()) {
                401 -> SignInError.Unauthorized.left()
                else -> SignInError.ConnectionMissing.left()
            }
        }
    }
}