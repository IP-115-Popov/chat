package com.eltex.data.repository

import com.eltex.data.storage.SharedPreferencesAuthDataStorage
import com.eltex.domain.feature.autorization.repository.AuthDataRepository
import com.eltex.domain.models.AuthData
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