package com.eltex.chat.feature.authorization.repository

import com.eltex.chat.feature.authorization.models.AuthData

interface AuthDataRepository {
    suspend fun saveAuthData(authData: AuthData)
    suspend fun getAuthData(): AuthData?
}