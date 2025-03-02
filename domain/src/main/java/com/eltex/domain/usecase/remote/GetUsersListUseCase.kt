package com.eltex.domain.usecase.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.DataError
import com.eltex.domain.models.ProfileModel
import com.eltex.domain.models.UserModel
import com.eltex.domain.repository.remote.UsersRemoteRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Duration
import java.time.Instant

class GetUsersListUseCase(
    private val usersRemoteRepository: UsersRemoteRepository,
) {
    private data class CachedData(
        val userList: List<UserModel>,
        val timestamp: Instant
    )

    private var cachedData: CachedData? = null
    private val mutex = Mutex()
    private val cacheExpirationDuration = Duration.ofSeconds(15)

    suspend operator fun invoke(
        query: String
    ): Either<DataError, List<UserModel>> {
        mutex.withLock {
            val currentCachedData = cachedData
            if (currentCachedData != null &&
                Instant.now().isBefore(currentCachedData.timestamp.plus(cacheExpirationDuration))
            ) {
                val filteredList = currentCachedData.userList.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.username.contains(query, ignoreCase = true)
                }
                if (filteredList.isNotEmpty()) {
                    return Either.Right(filteredList)
                }
            }
        }

            val usersResult = usersRemoteRepository.getUsersList(
                query = query,
                count = 100000,
                offset = 0,
            )

            return when (usersResult) {
                is Either.Left -> usersResult
                is Either.Right -> {
                    val users = usersResult.value.filter {
                        it.name.contains(query, ignoreCase = true) || it.username.contains(
                            query,
                            ignoreCase = true
                        )
                    }
                    cachedData = CachedData(users, Instant.now())
                    mutex.withLock {
                        if (users.isNotEmpty()) {
                            cachedData = CachedData(usersResult.value, Instant.now())
                            users.right()
                        }
                        else DataError.DefaultError.left()
                    }
                }
            }
    }
}