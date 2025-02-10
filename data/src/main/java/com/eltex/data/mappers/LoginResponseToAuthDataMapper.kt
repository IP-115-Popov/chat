package com.eltex.data.mappers

import com.eltex.data.models.authdata.LoginResponse
import com.eltex.domain.models.AuthData

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