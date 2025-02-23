package com.eltex.data.api

import com.eltex.data.models.members.MembersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MembersApi {
    @GET("/api/v1/channels.members")
    suspend fun getChannelMembers(
        @Query("roomId") roomId: String,
    ): Response<MembersResponse>
    @GET("/api/v1/groups.members")
    suspend fun getGroupMembers(
        @Query("roomId") roomId: String,
    ): Response<MembersResponse>
}