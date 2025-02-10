package com.eltex.data.repository

import com.eltex.data.api.OkHttpClientFactory
import com.eltex.domain.feature.signin.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor() : TokenRepository {
    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }
}