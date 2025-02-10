package com.eltex.data.storage

import com.eltex.domain.models.AuthData

interface AuthDataStorage {
    fun saveAuthData(authData: AuthData)
    fun getAuthData(): AuthData?
}