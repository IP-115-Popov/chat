package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.AuthDataLocalRepository
import com.eltex.domain.repository.ProfileInfoRemoteRepository

class GetProfileInfoUseCase(
    private val profileInfoRemoteRepository: ProfileInfoRemoteRepository,
    private val authDataLocalRepository: AuthDataLocalRepository,
) {
    suspend fun execute(): Either<DataError, ProfileModel> {
        val authData: AuthData? = authDataLocalRepository.getAuthData()
        if (authData != null) {
            val result = profileInfoRemoteRepository.getProfileInfo(
                userId = authData.userId, authToken = authData.authToken
            )
            when (result) {
                is Either.Left -> {
                    return with(authData) {
                        ProfileModel(
                            id = userId,
                            name = name,
                            avatarUrl = avatarUrl,
                            authToken = authToken,
                        )
                    }.right()
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