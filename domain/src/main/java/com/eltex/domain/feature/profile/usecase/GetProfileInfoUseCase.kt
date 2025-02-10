package com.eltex.domain.feature.profile.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.feature.autorization.repository.AuthDataRepository
import com.eltex.domain.feature.profile.repository.ProfileNetworkInfoRepository
import com.eltex.domain.models.AuthData
import com.eltex.domain.models.ProfileInfoError
import com.eltex.domain.models.ProfileModel

class GetProfileInfoUseCase(
    private val profileNetworkInfoRepository: ProfileNetworkInfoRepository,
    private val authDataRepository: AuthDataRepository,
) {
    suspend fun execute(): Either<ProfileInfoError, ProfileModel> {
        val authData: AuthData? = authDataRepository.getAuthData()
         if (authData != null) {
            val result = profileNetworkInfoRepository.getProfileInfo(
                userId = authData.userId, authToken = authData.authToken
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
             return ProfileInfoError.LocalStorage.left()
        }
    }
}