package com.eltex.data.mappers

import com.eltex.data.models.authdata.LoginRequest
import com.eltex.domain.models.LoginModel

object LoginModelToLoginRequestMapper {
    fun map(loginModel: LoginModel): LoginRequest = with(loginModel) {
        LoginRequest(
            user = user,
            password = password
        )
    }
}