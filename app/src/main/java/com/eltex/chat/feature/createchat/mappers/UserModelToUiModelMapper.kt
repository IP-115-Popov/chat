package com.eltex.chat.feature.createchat.mappers

import com.eltex.chat.feature.createchat.model.UserUiModel
import com.eltex.domain.models.UserModel

object UserModelToUiModelMapper {
    fun map(userModel: UserModel) = with(userModel) {
        UserUiModel(
            _id = _id,
            active = active,
            name = name,
            status = status,
            username = username,
            avatar = null,
        )
    }
}