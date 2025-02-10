package com.eltex.domain.feature.signin.repository

interface TokenRepository {
    suspend fun setToken(authToken: String)
}