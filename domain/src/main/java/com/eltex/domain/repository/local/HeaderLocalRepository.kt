package com.eltex.domain.repository.local

interface HeaderLocalRepository {
    suspend fun setToken(authToken: String)
    suspend fun setUserID(userID: String)
}