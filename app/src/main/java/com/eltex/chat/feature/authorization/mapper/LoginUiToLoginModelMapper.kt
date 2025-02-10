package com.eltex.chat.feature.authorization.mapper

import com.eltex.chat.feature.authorization.models.LoginUiModel
import com.eltex.domain.models.LoginModel

object LoginUiToLoginModelMapper {
    fun map(loginUiModel: LoginUiModel): LoginModel = with(loginUiModel) {
        LoginModel(
            user = user,
            password = password,
        )
    }
}