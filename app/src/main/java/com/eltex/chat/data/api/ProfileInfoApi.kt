package com.eltex.chat.data.api

import com.eltex.chat.data.models.profileinfo.ProfileInfoRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileInfoApi {
    @GET("/api/v1/me")
    suspend fun getProfileInfo(
        @Header("X-User-Id") userId: String,
        @Header("X-Auth-Token") xAuthToken: String
    ): Response<ProfileInfoRequest>
}