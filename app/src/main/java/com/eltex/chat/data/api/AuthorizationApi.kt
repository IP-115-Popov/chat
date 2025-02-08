package com.eltex.chat.data.api

import com.eltex.chat.data.models.LoginRequest
import com.eltex.chat.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {
    @POST("/api/v1/login")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<LoginResponse>
}