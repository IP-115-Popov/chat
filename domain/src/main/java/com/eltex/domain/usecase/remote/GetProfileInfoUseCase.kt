package com.eltex.domain.usecase.remote

import arrow.core.Either
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GetProfileInfoUseCase(
    private val profileInfoRemoteRepository: ProfileInfoRemoteRepository,
) {
    private var cachedProfile: ProfileModel? = null
    private val mutex = Mutex()

    suspend operator fun invoke(): Either<DataError, ProfileModel> {
        mutex.withLock {
            if (cachedProfile != null) {
                return Either.Right(cachedProfile!!)
            }
        }

        val result = profileInfoRemoteRepository.getProfileInfo()

        return when (result) {
            is Either.Right -> {
                mutex.withLock {
                    cachedProfile = result.value
                }
                result
            }
            is Either.Left -> result
        }
    }
}