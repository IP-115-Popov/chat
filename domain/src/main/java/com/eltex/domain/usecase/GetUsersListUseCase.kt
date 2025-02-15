package com.eltex.domain.usecase

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.UsersRemoteRepository

class GetUsersListUseCase(
    private val usersRemoteRepository: UsersRemoteRepository,
) {
    suspend fun execute(
        query: String, count: Int, offset: Int
    ): Either<DataError, List<UserModel>> = usersRemoteRepository.getUsersList(
        query = query,
        count = count,
        offset = offset,
    )
}