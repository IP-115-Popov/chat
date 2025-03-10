package com.eltex.data.api

import com.eltex.data.models.users.UserResponse
import com.eltex.data.models.users.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("/api/v1/users.list")
    suspend fun getUsersList(
        @Query("count") count: Int,
        @Query("offset") offset: Int,
        @Query("query") query: String,
    ): Response<UsersResponse>

    @GET("/api/v1/users.info")
    suspend fun getUser(
        @Query("userId") userId: String
    ): Response<UserResponse>
}