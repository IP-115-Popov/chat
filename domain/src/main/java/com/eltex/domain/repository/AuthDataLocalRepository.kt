package com.eltex.domain.repository

import com.eltex.domain.models.AuthData

interface AuthDataLocalRepository {
    suspend fun saveAuthData(authData: AuthData)
    suspend fun getAuthData(): AuthData?
}