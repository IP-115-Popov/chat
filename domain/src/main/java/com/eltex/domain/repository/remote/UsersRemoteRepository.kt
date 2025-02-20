package com.eltex.domain.repository.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel

interface UsersRemoteRepository {
    suspend fun getUsersList(
        count: Int, offset: Int, query: String
    ): Either<DataError, List<UserModel>>

    suspend fun getUser(
        userId: String
    ): Either<DataError, UserModel>
}