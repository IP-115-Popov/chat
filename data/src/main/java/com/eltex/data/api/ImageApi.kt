package com.eltex.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ImageApi {
    @Streaming
    @GET
    suspend fun getImage(@Url imageUrl: String): ResponseBody
}