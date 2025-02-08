package com.eltex.chat.data.repository

import com.eltex.chat.data.api.OkHttpClientFactory
import com.eltex.chat.data.storage.SharedPreferencesTokenStorage
import com.eltex.chat.feature.authorization.repository.TokenRepository
import javax.inject.Inject


class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferencesTokenStorage: SharedPreferencesTokenStorage
) : TokenRepository {
    override suspend fun saveToken(authToken: String) {
        sharedPreferencesTokenStorage.saveToken(token = authToken)
    }

    override suspend fun getToken(): String? {
        val token = sharedPreferencesTokenStorage.getToken()
        if (token != null) {
            return token
        } else {
            return null
        }
    }

    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }
}