package com.eltex.data.api

import com.eltex.data.models.chatinfo.ChatInfoResponse
import com.eltex.data.models.createchat.CreateChatRequest
import com.eltex.data.models.createchat.CreatedChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ChatApi {
    @POST("/api/v1/im.create")
    suspend fun createChat(
        @Body createChatRequest: CreateChatRequest
    ): Response<CreatedChatResponse>

    @GET("/api/v1/rooms.info")
    suspend fun getChatInfo(
        @Query("roomId") roomId: String
    ): Response<ChatInfoResponse>
}