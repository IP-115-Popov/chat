package com.eltex.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.AuthorizationApi
import com.eltex.data.mappers.LoginModelToLoginRequestMapper
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.LoginModel
import com.eltex.domain.models.SignInError
import com.eltex.domain.repository.SignInNetworkRepository
import javax.inject.Inject

class SignInNetworkRepositoryImpl @Inject constructor(
    private val authorizationApi: AuthorizationApi
) : SignInNetworkRepository {
    override suspend fun signIn(loginModel: LoginModel): Either<SignInError, AuthData> {
        try {
            val response = authorizationApi.signIn(
                loginRequest = LoginModelToLoginRequestMapper.map(loginModel)
            )

            return if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    com.eltex.data.mappers.LoginResponseToAuthDataMapper.map(loginResponse).right()
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
        } catch (e: Exception) {
            Log.e("SignInNetworkRepositoryImpl", "e ${e.message}")
            e.printStackTrace()
            return SignInError.ConnectionMissing.left()
        }
    }
}