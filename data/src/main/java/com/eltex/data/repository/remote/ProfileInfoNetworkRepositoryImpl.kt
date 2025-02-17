package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ProfileInfoApi
import com.eltex.data.mappers.ProfileInfoRequestToProfileModelMapper
import com.eltex.data.models.profileinfo.ProfileInfoRequest
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository
import retrofit2.Response
import javax.inject.Inject

class ProfileInfoNetworkRepositoryImpl @Inject constructor(
    private val profileInfoApi: ProfileInfoApi
) : ProfileInfoRemoteRepository {
    override suspend fun getProfileInfo(): Either<DataError, ProfileModel> {
        val response: Response<ProfileInfoRequest>
        try {
            response = profileInfoApi.getProfileInfo()
        } catch (e: Exception) {
            Log.e("ProfileInfoNetworkRepositoryImpl", "getProfileInfo ${e.message}")
            e.printStackTrace()
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