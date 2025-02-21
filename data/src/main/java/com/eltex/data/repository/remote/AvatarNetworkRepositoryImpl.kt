package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.AvatarApi
import com.eltex.data.api.HeaderManagerImpl
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.AvatarRemoteRepository
import javax.inject.Inject

class AvatarNetworkRepositoryImpl @Inject constructor(
    private val avatarApi: AvatarApi,
    private val headerManagerImpl: HeaderManagerImpl,
) : AvatarRemoteRepository {
    override suspend fun getAvatar(
        subject: String
    ): Either<DataError, ByteArray> {
        return try {
            if (headerManagerImpl.id != null && headerManagerImpl.token != null) {
                val avatarRes = avatarApi.get(
                    subject = subject,
                    rc_uid = headerManagerImpl.id!!,
                    rc_token = headerManagerImpl.token!!
                )
                val bytes = avatarRes.bytes()
                bytes.right()
            } else {
                DataError.IncorrectData.left()
            }
        } catch (e: Exception) {
            Log.e("AvatarRemoteRepository", "getAvatar ${e.message}")
            e.printStackTrace()
            DataError.DefaultError.left()
        }
    }
}