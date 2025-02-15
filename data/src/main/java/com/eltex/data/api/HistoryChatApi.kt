package com.eltex.data.api

import com.eltex.data.models.hitorymessge.HistoryMsgResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryChatApi {
    @GET("/api/v1/groups.history")
    suspend fun getGroupsHistory(
        @Query("roomId") roomId: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
    ): Response<HistoryMsgResponse>

    @GET("/api/v1/channels.history")
    suspend fun getChannelsHistory(
        @Query("roomId") roomId: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
    ): Response<HistoryMsgResponse>

    @GET("/api/v1/im.history")
    suspend fun getImHistory(
        @Query("roomId") roomId: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int,
    ): Response<HistoryMsgResponse>
}