package com.eltex.domain.repository

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel

interface ProfileNetworkInfoRepository {
    suspend fun getProfileInfo(userId: String, authToken: String): Either<DataError, ProfileModel>
}