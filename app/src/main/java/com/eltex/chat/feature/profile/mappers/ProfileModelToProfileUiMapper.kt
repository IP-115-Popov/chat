package com.eltex.chat.feature.profile.mappers

import com.eltex.chat.feature.profile.models.ProfileUiModel
import com.eltex.domain.models.ProfileModel

object ProfileModelToProfileUiMapper {
    fun map(profileModel: ProfileModel) = with(profileModel) {
        ProfileUiModel(
            id = id,
            avatarUrl = avatarUrl,
            name = name,
            authToken = authToken,
        )
    }
}