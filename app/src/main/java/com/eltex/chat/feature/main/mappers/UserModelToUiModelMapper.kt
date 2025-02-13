package com.eltex.chat.feature.main.mappers

import com.eltex.chat.feature.main.models.UserUiModel
import com.eltex.domain.models.UserModel

object UserModelToUiModelMapper {
    fun map(userModel: UserModel) = with(userModel) {
        UserUiModel(
            _id = _id,
            active = active,
            name = name,
            status = status,
             username = username,
        )
    }
}