package com.eltex.data.api

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(
    private var token: String? = null,
    private var userId: String? = null,
) : Interceptor {

    fun updateToken(token: String?) {
        this.token = token
    }

    fun updateUserId(userId: String?) {
        this.userId = userId
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Api-Token", it)
            requestBuilder.addHeader("X-Auth-Token", it)
        }
        userId?.let {
            requestBuilder.addHeader("X-User-Id", it)
        }
        return chain.proceed(requestBuilder.build())
    }
}
