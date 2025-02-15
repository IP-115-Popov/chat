package com.eltex.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ProfileInfoApi
import com.eltex.data.mappers.ProfileInfoRequestToProfileModelMapper
import com.eltex.data.models.profileinfo.ProfileInfoRequest
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.ProfileInfoRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileInfoRemoteRepositoryImpl @Inject constructor(
    private val profileInfoApi: ProfileInfoApi
) : ProfileInfoRemoteRepository {
    override suspend fun getProfileInfo(): Either<DataError, ProfileModel> {
        val response: Response<ProfileInfoRequest>
        try {
            response = profileInfoApi.getProfileInfo()
        } catch (e: Exception) {
            return DataError.ConnectionMissing.left()
        }

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                ProfileInfoRequestToProfileModelMapper.map(loginResponse).right()
            } else {
                DataError.ConnectionMissing.left()
            }

        } else {
            DataError.ConnectionMissing.left()
        }
    }
}