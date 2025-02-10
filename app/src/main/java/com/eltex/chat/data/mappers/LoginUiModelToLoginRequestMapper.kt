package com.eltex.chat.data.mappers

import com.eltex.chat.data.models.authdata.LoginRequest
import com.eltex.chat.feature.authorization.models.LoginUiModel

object LoginUiModelToLoginRequestMapper {
    fun map(loginUiModel: LoginUiModel): LoginRequest = with(loginUiModel) {
        LoginRequest(
            user = user,
            password = password
        )
    }
}