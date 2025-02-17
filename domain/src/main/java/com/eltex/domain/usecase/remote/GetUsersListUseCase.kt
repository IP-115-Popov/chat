package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.remote.UsersRemoteRepository

class GetUsersListUseCase(
    private val usersRemoteRepository: UsersRemoteRepository,
) {
    suspend operator fun invoke(
        query: String, count: Int, offset: Int
    ): Either<DataError, List<UserModel>> = usersRemoteRepository.getUsersList(
        query = query,
        count = count,
        offset = offset,
    )
}