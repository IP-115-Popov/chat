package com.eltex.chat.feature.profile.repository

import arrow.core.Either
import com.eltex.chat.feature.profile.models.ProfileUiModel

interface ProfileInfoRepository {
    suspend fun getProfileInfo(userId: String): Either<String, ProfileUiModel>
}