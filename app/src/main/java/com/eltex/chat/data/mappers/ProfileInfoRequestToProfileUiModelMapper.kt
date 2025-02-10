package com.eltex.chat.data.mappers

import com.eltex.chat.data.models.profileinfo.ProfileInfoRequest
import com.eltex.chat.feature.profile.models.ProfileUiModel

object ProfileInfoRequestToProfileUiModelMapper {
    fun map(profileInfoRequest: ProfileInfoRequest): ProfileUiModel = with(profileInfoRequest) {
        ProfileUiModel(
             id = _id,
             name = name,
             avatarUrl = avatarUrl,
        )
    }
}