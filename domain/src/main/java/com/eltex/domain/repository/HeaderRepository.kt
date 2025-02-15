package com.eltex.domain.repository

interface HeaderRepository {
    suspend fun setToken(authToken: String)
    suspend fun setUserID(userID: String)
}