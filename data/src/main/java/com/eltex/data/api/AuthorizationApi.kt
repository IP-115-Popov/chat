package com.eltex.data.api

import com.eltex.data.models.authdata.LoginRequest
import com.eltex.data.models.authdata.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {
    @POST("/api/v1/login")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<LoginResponse>
}