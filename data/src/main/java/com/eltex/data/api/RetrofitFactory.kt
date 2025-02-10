package com.eltex.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().client(client).baseUrl("https://apiexplorer.support.rocket.chat/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}