package com.eltex.data.storage

import com.eltex.data.models.AuthDataEntity

interface AuthDataStorage {
    fun saveAuthData(authData: AuthDataEntity)
    fun getAuthData(): AuthDataEntity?
    fun deleteAuthData()
}