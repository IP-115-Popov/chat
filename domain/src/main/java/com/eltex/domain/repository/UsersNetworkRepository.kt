package com.eltex.domain.repository

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.models.UserModel

interface UsersNetworkRepository {
    suspend fun getUsersList(
        count: Int, offset: Int, query: String, userId: String, xAuthToken: String
    ): Either<DataError, List<UserModel>>
}