package com.eltex.domain.usecase

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.ProfileInfoRemoteRepository

class GetProfileInfoUseCase(
    private val profileInfoRemoteRepository: ProfileInfoRemoteRepository,
) {
    suspend fun execute(): Either<DataError, ProfileModel> =
        profileInfoRemoteRepository.getProfileInfo()
}