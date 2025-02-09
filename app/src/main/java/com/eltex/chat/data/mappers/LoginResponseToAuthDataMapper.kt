package com.eltex.chat.data.mappers

import com.eltex.chat.data.models.LoginResponse
import com.eltex.chat.models.AuthData

object LoginResponseToAuthDataMapper {
    fun map(loginResponse: LoginResponse): AuthData =
        AuthData(
            authToken = loginResponse.data.authToken,
            userId = loginResponse.data.userId,
            avatarUrl = loginResponse.data.me.avatarUrl
        )
}