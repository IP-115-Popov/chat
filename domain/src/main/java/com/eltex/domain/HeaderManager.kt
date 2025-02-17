package com.eltex.domain

interface HeaderManager {
    suspend fun setToken(authToken: String)
    suspend fun setUserID(userID: String)
}