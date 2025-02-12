package com.eltex.data.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal object RetrofitFactory {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().client(client).baseUrl("https://eltexios2025.rocket.chat")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
}