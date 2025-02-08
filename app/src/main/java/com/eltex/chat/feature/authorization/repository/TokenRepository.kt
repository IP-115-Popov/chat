package com.eltex.chat.feature.authorization.repository

interface TokenRepository {
    suspend fun saveToken(authToken: String)
    suspend fun getToken(): String?
    suspend fun setToken(authToken: String)
}