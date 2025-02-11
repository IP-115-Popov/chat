package com.eltex.domain.repository

interface TokenRepository {
    suspend fun setToken(authToken: String)
}