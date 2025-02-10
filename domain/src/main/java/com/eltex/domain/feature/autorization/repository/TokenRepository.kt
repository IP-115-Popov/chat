package com.eltex.domain.feature.autorization.repository

interface TokenRepository {
    suspend fun setToken(authToken: String)
}