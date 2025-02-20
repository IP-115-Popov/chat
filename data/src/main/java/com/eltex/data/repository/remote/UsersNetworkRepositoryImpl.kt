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

class UsersNetworkRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersRemoteRepository {
    override suspend fun getUsersList(
        count: Int,
        offset: Int,
        query: String,
    ): Either<DataError, List<UserModel>> {

        val jsonQuery = JSONObject()
            .put("name", JSONObject().put("\$regex", query))

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

    override suspend fun getUser(userId: String): Either<DataError, UserModel> {
        return try {
            val userResponse = usersApi.getUser(userId = userId)
            if (userResponse.isSuccessful) {
                userResponse.body()?.let { user ->
                    UserDTOToUserModelMapper.map(user.user).right()
                } ?: DataError.DefaultError.left()

            } else {
                when (userResponse.code()) {
                    in 400 until 500 -> DataError.IncorrectData.left()
                    else -> DataError.DefaultError.left()
                }
            }
        } catch (e: Exception) {
            Log.e("UsersRemoteRepository", "getUser ${e.message}")
            DataError.ConnectionMissing.left()
        }

    }
}