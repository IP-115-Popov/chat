package com.eltex.data.repository.remote

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.AvatarApi
import com.eltex.data.api.HeaderManagerImpl
import com.eltex.domain.HeaderManager
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.remote.AvatarRemoteRepository
import javax.inject.Inject

class AvatarNetworkRepositoryImpl @Inject constructor(
    private val avatarApi: AvatarApi,
    private val headerManager: HeaderManager,
) : AvatarRemoteRepository {
    override suspend fun getAvatar(
        subject: String
    ): Either<DataError, ByteArray> {
        val userId = headerManager.getUserID()
        val token = headerManager.getToken()
        return try {
            if (userId != null && token != null) {
                val avatarRes = avatarApi.get(
                    subject = subject,
                    rc_uid = userId,
                    rc_token = token
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

    override suspend fun getRoomAvatar(roomId: String): Either<DataError, ByteArray> {
        val userId = headerManager.getUserID()
        val token = headerManager.getToken()
        return try {
            if (userId != null && token != null) {
                val avatarRes = avatarApi.getRoomAvatar(
                    roomId = roomId,
                    rc_uid = userId,
                    rc_token = token
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