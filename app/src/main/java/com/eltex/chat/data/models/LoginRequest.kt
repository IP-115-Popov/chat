package com.eltex.chat.data.models

import com.eltex.chat.feature.authorization.models.LoginUiModel

data class LoginRequest(
    val user: String = "",
    val password: String = "",
) {
    companion object {
        fun toLoginRequest(loginUiModel: LoginUiModel): LoginRequest = with(loginUiModel) {
            LoginRequest(
                user = user,
                password = password
            )
        }
    }

    fun fromLoginRequest(): LoginRequest = LoginRequest(
        user = user,
        password = password
    )
}