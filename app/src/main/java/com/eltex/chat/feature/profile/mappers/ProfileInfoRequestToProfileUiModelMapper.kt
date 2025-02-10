package com.eltex.chat.feature.profile.mappers

import com.eltex.chat.feature.profile.models.ProfileUiModel
import com.eltex.data.models.profileinfo.ProfileInfoRequest

object ProfileInfoRequestToProfileUiModelMapper {
    fun map(profileInfoRequest: ProfileInfoRequest): ProfileUiModel = with(profileInfoRequest) {
        ProfileUiModel(
            id = _id,
            name = name,
            avatarUrl = avatarUrl,
        )
    }
}