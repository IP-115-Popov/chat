package com.eltex.chat.data.storage

import com.eltex.chat.models.AuthData

interface AuthDataStorage {
    fun saveAuthData(authData: AuthData)
    fun getAuthData(): AuthData?
}