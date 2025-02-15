package com.eltex.data.api

import com.eltex.data.models.createchat.CreateChatRequest
import com.eltex.data.models.createchat.CreatedChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ChatCreationApi {
    @POST("/api/v1/im.create")
    suspend fun createChat(
        @Header("X-User-Id") userId: String,
        @Header("X-Auth-Token") xAuthToken: String,
        @Body createChatRequest: CreateChatRequest
    ): Response<CreatedChatResponse>
}