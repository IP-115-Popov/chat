package com.eltex.chat.feature.authorization.mapper

import com.eltex.chat.feature.authorization.models.LoginUiModel
import com.eltex.data.models.authdata.LoginRequest

object LoginUiModelToLoginRequestMapper {
    fun map(loginUiModel: LoginUiModel): LoginRequest = with(loginUiModel) {
        LoginRequest(
            user = user,
            password = password
        )
    }
}