package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.UsersNetworkRepository

class GetUsersListUseCase(
    private val usersNetworkRepository: UsersNetworkRepository,
    private val authDataRepository: AuthDataRepository,
) {
    suspend fun execute(
        query: String, count: Int, offset: Int
    ): Either<DataError, List<UserModel>> {
        val authData: AuthData? = authDataRepository.getAuthData()
        if (authData != null) {
            val result = usersNetworkRepository.getUsersList(
                query = query,
                count = count,
                offset = offset,
                userId = authData.userId,
                xAuthToken = authData.authToken,
            )
            when (result) {
                is Either.Left -> {
                    return result.value.left()
                }

                is Either.Right -> {
                    return result.value.right()
                }
            }
        } else {
            return DataError.LocalStorage.left()
        }
    }
}