package com.eltex.domain.feature.profile.repository

import arrow.core.Either
import com.eltex.domain.models.ProfileInfoError
import com.eltex.domain.models.ProfileModel

interface ProfileNetworkInfoRepository {
    suspend fun getProfileInfo(userId: String, authToken: String): Either<ProfileInfoError, ProfileModel>
}