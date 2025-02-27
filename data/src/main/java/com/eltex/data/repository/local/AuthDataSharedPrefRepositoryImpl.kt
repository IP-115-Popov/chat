package com.eltex.data.repository.local

import com.eltex.data.models.AuthDataEntity
import com.eltex.data.storage.SharedPreferencesAuthDataStorage
import com.eltex.domain.models.AuthData
import com.eltex.domain.repository.local.AuthDataLocalRepository
import javax.inject.Inject


class AuthDataSharedPrefRepositoryImpl @Inject constructor(
    private val sharedPreferencesAuthDataStorage: SharedPreferencesAuthDataStorage
) : AuthDataLocalRepository {

    override suspend fun saveAuthData(authData: AuthData) {
        val authDataEntity = AuthDataEntity(
            userId = authData.userId,
            name = authData.name,
            authToken = authData.authToken,
            avatarUrl = authData.avatarUrl,
        )
        sharedPreferencesAuthDataStorage.saveAuthData(authData = authDataEntity)
    }

    override suspend fun getAuthData(): AuthData? {
        val user = sharedPreferencesAuthDataStorage.getAuthData()
        val authData = AuthData(
            userId = user?.userId ?: return null,
            name = user?.name ?: return null,
            authToken = user?.authToken ?: return null,
            avatarUrl = user?.avatarUrl ?: return null,
        )
        return authData
    }

    override suspend fun deleteAuthData() {
        sharedPreferencesAuthDataStorage.deleteAuthData()
    }
}