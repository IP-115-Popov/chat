package com.eltex.chat.data.repository

import com.eltex.chat.data.api.OkHttpClientFactory
import com.eltex.chat.feature.authorization.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor() : TokenRepository {
    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }
}