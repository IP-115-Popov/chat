package com.eltex.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.UsersApi
import com.eltex.data.mappers.ProfileInfoRequestToProfileModelMapper
import com.eltex.data.mappers.UserDTOToUserModelMapper
import com.eltex.data.models.profileinfo.ProfileInfoRequest
import com.eltex.data.models.users.UsersResponse
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.UsersNetworkRepository
import retrofit2.Response
import javax.inject.Inject

class UsersNetworkRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersNetworkRepository {
    override suspend fun getUsersList(
        count: Int,
        offset: Int,
        query: String,
        userId: String,
        xAuthToken: String
    ): Either<DataError, List<UserModel>> {

        val response: Response<UsersResponse>

        try {
            response = usersApi.getUsersList(count = count, offset = offset, query = query , userId = userId, xAuthToken = xAuthToken)
        } catch (e: Exception) {
            Log.e("response",e.message ?: "")
            e.printStackTrace()
            return DataError.ConnectionMissing.left()
        }

        return if (response.isSuccessful) {
            val loginResponse = response.body()?.users
            if (loginResponse != null) {
                loginResponse.map {
                    UserDTOToUserModelMapper.map(it)
                }.right()

            } else {
                DataError.ConnectionMissing.left()
            }

        } else {
            DataError.ConnectionMissing.left()
        }

    }
}