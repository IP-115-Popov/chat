package com.eltex.chat.data.storage

import com.eltex.chat.models.User

interface UserStorage {
    fun saveUser(user: User)
    fun getUser(): User?
}