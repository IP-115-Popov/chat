package com.eltex.domain.repository

interface HeaderLocalRepository {
    suspend fun setToken(authToken: String)
    suspend fun setUserID(userID: String)
}