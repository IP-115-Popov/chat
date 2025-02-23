package com.eltex.domain.usecase.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.remote.UsersRemoteRepository

class GetUsersListUseCase(
    private val usersRemoteRepository: UsersRemoteRepository,
) {
    suspend operator fun invoke(
        query: String
    ): Either<DataError, List<UserModel>> {
        val usersResult = usersRemoteRepository.getUsersList(
            query = query,
            count = 100000,
            offset = 0,
        )

        return when (usersResult) {
            is Either.Left -> usersResult
            is Either.Right -> {
                val users = usersResult.value.filter {
                    it.name.contains(query, ignoreCase = true) || it.username.contains(
                        query,
                        ignoreCase = true
                    )
                }
                if (users.isNotEmpty()) users.right()
                else DataError.DefaultError.left()
            }
        }
    }
}