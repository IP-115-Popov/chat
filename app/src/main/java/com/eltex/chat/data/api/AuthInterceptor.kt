package com.eltex.chat.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private var token: String? = null) : Interceptor {
    fun updateToken(token: String?) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Api-Token", it)
        }
        return chain.proceed(requestBuilder.build())
    }
}
