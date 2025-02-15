package com.eltex.data.api

import com.eltex.data.models.profileinfo.ProfileInfoRequest
import retrofit2.Response
import retrofit2.http.GET

interface ProfileInfoApi {
    @GET("/api/v1/me")
    suspend fun getProfileInfo(): Response<ProfileInfoRequest>
}