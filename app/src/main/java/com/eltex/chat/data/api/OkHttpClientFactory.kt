package com.eltex.chat.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {

    private val authInterceptor = AuthInterceptor()

    fun setApiToken(token: String) {
        authInterceptor.updateToken(token)
    }

    fun getOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
}