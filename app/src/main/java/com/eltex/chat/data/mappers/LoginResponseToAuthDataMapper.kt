package com.eltex.chat.data.mappers

import com.eltex.chat.data.models.authdata.LoginResponse
import com.eltex.chat.feature.authorization.models.AuthData

object LoginResponseToAuthDataMapper {
    fun map(loginResponse: LoginResponse): AuthData = with(loginResponse) {
        AuthData(
            authToken = data.authToken,
            name = data.me.name,
            userId = data.userId,
            avatarUrl = data.me.avatarUrl
        )
    }
}