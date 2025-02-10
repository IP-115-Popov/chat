package com.eltex.chat.feature.profile.repository

import arrow.core.Either
import com.eltex.chat.feature.profile.models.ProfileUiModel

interface ProfileNetworkInfoRepository {
    suspend fun getProfileInfo(userId: String, authToken: String): Either<String, ProfileUiModel>
}