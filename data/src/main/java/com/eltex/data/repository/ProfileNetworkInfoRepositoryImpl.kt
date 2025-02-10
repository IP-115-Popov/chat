package com.eltex.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ProfileInfoApi
import com.eltex.data.mappers.ProfileInfoRequestToProfileModelMapper
import com.eltex.data.models.profileinfo.ProfileInfoRequest
import com.eltex.domain.feature.profile.repository.ProfileNetworkInfoRepository
import com.eltex.domain.models.ProfileInfoError
import com.eltex.domain.models.ProfileModel
import retrofit2.Response
import javax.inject.Inject

class ProfileNetworkInfoRepositoryImpl @Inject constructor(
    private val profileInfoApi: ProfileInfoApi
) : ProfileNetworkInfoRepository {
    override suspend fun getProfileInfo(
        userId: String,
        authToken: String,
    ): Either<ProfileInfoError, ProfileModel> {
        val response: Response<ProfileInfoRequest>
        try {
            response = profileInfoApi.getProfileInfo(userId, authToken)
        } catch (e: Exception) {
            return ProfileInfoError.ConnectionMissing.left()
        }

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