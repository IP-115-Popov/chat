package com.eltex.data.api

import com.eltex.data.models.users.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UsersApi {
    @GET("/api/v1/users.list")
    suspend fun getUsersList(
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("query") query: String,
        @Header("X-User-Id") userId: String,
        @Header("X-Auth-Token") xAuthToken: String
    ): Response<UsersResponse>
}