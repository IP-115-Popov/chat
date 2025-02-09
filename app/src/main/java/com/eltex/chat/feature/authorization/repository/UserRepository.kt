package com.eltex.chat.feature.authorization.repository

import com.eltex.chat.models.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
}