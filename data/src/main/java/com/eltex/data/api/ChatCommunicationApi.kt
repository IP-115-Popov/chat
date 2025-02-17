package com.eltex.data.api

import com.eltex.data.models.communication.TextMessagePayload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ChatCommunicationApi {
    @POST("/api/v1/chat.sendMessage")
    suspend fun sendTextMessage(@Body textMessagePayload: TextMessagePayload)

    @Multipart
    @POST("/api/v1/rooms.upload/{roomId}")
    suspend fun uploadFile(
        @Path("roomId") roomId: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    )
}