package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository

class GetProfileInfoUseCase(
    private val profileInfoRemoteRepository: ProfileInfoRemoteRepository,
) {
    suspend fun execute(): Either<DataError, ProfileModel> =
        profileInfoRemoteRepository.getProfileInfo()
}