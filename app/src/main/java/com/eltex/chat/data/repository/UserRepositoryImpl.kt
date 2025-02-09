package com.eltex.chat.data.repository

import com.eltex.chat.data.storage.SharedPreferencesTokenStorage
import com.eltex.chat.feature.authorization.repository.UserRepository
import com.eltex.chat.models.User
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val sharedPreferencesTokenStorage: SharedPreferencesTokenStorage
) : UserRepository {

    override suspend fun saveUser(user: User) {
        sharedPreferencesTokenStorage.saveUser(user = user)
    }

    override suspend fun getUser(): User? {
        val user = sharedPreferencesTokenStorage.getUser()
        return user
    }
}