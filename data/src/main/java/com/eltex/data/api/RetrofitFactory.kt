package com.eltex.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitFactory {
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().client(client).baseUrl("https://eltexios2025.rocket.chat")
            .addConverterFactory(GsonConverterFactory.create()).build()
}