package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.UsersApi
import com.eltex.data.mappers.UserDTOToUserModelMapper
import com.eltex.data.models.users.UsersResponse
import com.eltex.domain.models.DataError
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.remote.UsersRemoteRepository
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UsersRemoteRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersRemoteRepository {
    override suspend fun getUsersList(
        count: Int,
        offset: Int,
        query: String,
    ): Either<DataError, List<UserModel>> {

        val jsonQuery = JSONObject()
            .put("name", JSONObject().put("\$regex", query))

        Log.i("UsersNetworkRepositoryImpl", jsonQuery.toString())

        val response: Response<UsersResponse>

        try {
            response = usersApi.getUsersList(
                count = count,
                offset = offset,
                query = jsonQuery.toString(),
            )
        } catch (e: Exception) {
            Log.e("response", e.message ?: "")
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