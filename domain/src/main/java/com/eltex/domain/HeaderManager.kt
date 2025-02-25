package com.eltex.domain

interface HeaderManager {
    suspend fun setToken(authToken: String)
    suspend fun setUserID(userID: String)
    suspend fun getUserID(): String?
    suspend fun getToken(): String?
}