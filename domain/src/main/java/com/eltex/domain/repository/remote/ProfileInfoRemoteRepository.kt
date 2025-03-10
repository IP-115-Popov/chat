package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel

interface ProfileInfoRemoteRepository {
    suspend fun getProfileInfo(): Either<DataError, ProfileModel>
}