package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.AvatarApi
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.AvatarRemoteRepository
import javax.inject.Inject

class AvatarNetworkRepositoryImpl @Inject constructor(
    private val avatarApi: AvatarApi
) : AvatarRemoteRepository {
    override suspend fun getAvatar(
        subject: String, rc_uid: String, rc_token: String
    ): Either<DataError, ByteArray> {
        return try {
            val avatarRes = avatarApi.get(subject = subject, rc_uid = rc_uid, rc_token = rc_token)
            val bytes = avatarRes.bytes()
            bytes.right()
        } catch (e: Exception) {
            Log.e("AvatarRemoteRepository", "getAvatar ${e.message}")
            e.printStackTrace()
            DataError.DefaultError.left()
        }
    }
}