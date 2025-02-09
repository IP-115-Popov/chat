package com.eltex.chat.feature.authorization.repository

interface TokenRepository {
    suspend fun setToken(authToken: String)
}