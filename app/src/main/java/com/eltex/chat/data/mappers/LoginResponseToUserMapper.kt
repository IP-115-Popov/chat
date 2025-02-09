package com.eltex.chat.data.mappers

import com.eltex.chat.data.models.LoginResponse
import com.eltex.chat.models.User

object LoginResponseToUserMapper {
    fun map(loginResponse: LoginResponse): User =
        User(
            authToken = loginResponse.data.authToken,
            userId = loginResponse.data.userId,
            avatarUrl = loginResponse.data.me.avatarUrl
        )
}