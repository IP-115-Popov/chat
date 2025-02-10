package com.eltex.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ProfileInfoApi
import com.eltex.data.mappers.ProfileInfoRequestToProfileModelMapper
import com.eltex.domain.feature.profile.repository.ProfileNetworkInfoRepository
import com.eltex.domain.models.ProfileInfoError
import com.eltex.domain.models.ProfileModel
import javax.inject.Inject

class ProfileNetworkInfoRepositoryImpl @Inject constructor(
    private val profileInfoApi: ProfileInfoApi
) : ProfileNetworkInfoRepository {
    override suspend fun getProfileInfo(
        userId: String,
        authToken: String,
    ): Either<ProfileInfoError, ProfileModel> {
        val response = profileInfoApi.getProfileInfo(userId, authToken)

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                ProfileInfoRequestToProfileModelMapper.map(loginResponse).right()
            } else {
                ProfileInfoError.ConnectionMissing.left()
            }
        } else {
            ProfileInfoError.ConnectionMissing.left()
        }
    }
}