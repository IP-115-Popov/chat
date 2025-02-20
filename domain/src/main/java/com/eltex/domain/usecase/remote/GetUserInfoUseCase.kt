package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.remote.UsersRemoteRepository

class GetUserInfoUseCase(
    private val usersRemoteRepository: UsersRemoteRepository,
) {
    suspend operator fun invoke(userId: String): Either<DataError, UserModel> =
        usersRemoteRepository.getUser(userId = userId)
}