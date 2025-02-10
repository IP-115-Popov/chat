package com.eltex.chat.data.repository

import com.eltex.chat.data.storage.SharedPreferencesAuthDataStorage
import com.eltex.chat.feature.authorization.repository.AuthDataRepository
import com.eltex.chat.feature.authorization.models.AuthData
import javax.inject.Inject


class AuthDataRepositoryImpl @Inject constructor(
    private val sharedPreferencesAuthDataStorage: SharedPreferencesAuthDataStorage
) : AuthDataRepository {

    override suspend fun saveAuthData(authData: AuthData) {
        sharedPreferencesAuthDataStorage.saveAuthData(authData = authData)
    }

    override suspend fun getAuthData(): AuthData? {
        val user = sharedPreferencesAuthDataStorage.getAuthData()
        return user
    }
}