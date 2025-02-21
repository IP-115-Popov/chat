package com.eltex.data.mappers

import com.eltex.data.models.profileinfo.ProfileInfoRequest
import com.eltex.domain.models.ProfileModel

object ProfileInfoRequestToProfileModelMapper {
    fun map(profileInfoRequest: ProfileInfoRequest): ProfileModel = with(profileInfoRequest) {
        ProfileModel(
            id = _id,
            name = name,
            avatarUrl = avatarUrl,
            username = username,
        )
    }
}