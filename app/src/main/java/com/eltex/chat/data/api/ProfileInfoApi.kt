package com.eltex.chat.data.api

import com.eltex.chat.data.models.profileinfo.ProfileInfoRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileInfoApi {
    @GET("/api/v1/me")
    fun getProfileInfo(@Query("userId") userId: String): Response<ProfileInfoRequest>
}