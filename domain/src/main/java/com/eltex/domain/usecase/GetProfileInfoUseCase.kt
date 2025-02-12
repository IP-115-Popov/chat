package com.eltex.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.ProfileNetworkInfoRepository

class GetProfileInfoUseCase(
    private val profileNetworkInfoRepository: ProfileNetworkInfoRepository,
    private val authDataRepository: AuthDataRepository,
) {
    suspend fun execute(): Either<DataError, ProfileModel> {
        val authData: AuthData? = authDataRepository.getAuthData()
        if (authData != null) {
            val result = profileNetworkInfoRepository.getProfileInfo(
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