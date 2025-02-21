package com.eltex.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface AvatarApi {
    @Streaming
    @GET("/avatar/{subject}")
    suspend fun get(
        @Path("subject") subject: String,
        @Query("format") format: String = "png",
        @Query("size") size: Int = 200,
        @Query("rc_uid") rc_uid: String,
        @Query("rc_token") rc_token: String,
    ): ResponseBody

    @Streaming
    @GET("/avatar/room/{roomId}")
    suspend fun getRoomAvatar(
        @Path("roomId") roomId: String,
        @Query("format") format: String = "png",
        @Query("size") size: Int = 200,
        @Query("rc_uid") rc_uid: String,
        @Query("rc_token") rc_token: String,
    ): ResponseBody
}