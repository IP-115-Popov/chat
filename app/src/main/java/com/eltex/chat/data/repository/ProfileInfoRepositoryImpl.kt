package com.eltex.chat.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.chat.data.api.ProfileInfoApi
import com.eltex.chat.data.mappers.ProfileInfoRequestToProfileUiModelMapper
import com.eltex.chat.feature.profile.models.ProfileUiModel
import com.eltex.chat.feature.profile.repository.ProfileInfoRepository
import javax.inject.Inject

class ProfileInfoRepositoryImpl @Inject constructor(
    private val profileInfoApi: ProfileInfoApi
) : ProfileInfoRepository {
    override suspend fun getProfileInfo(userId: String): Either<String, ProfileUiModel> {
        val response = profileInfoApi.getProfileInfo(userId)

        return if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null) {
                ProfileInfoRequestToProfileUiModelMapper.map(loginResponse).right()
            } else {
                response.code().toString().left()
            }
        } else {
            response.code().toString().left()
        }
    }
}