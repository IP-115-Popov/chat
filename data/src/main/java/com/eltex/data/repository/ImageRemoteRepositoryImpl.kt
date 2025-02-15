package com.eltex.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.ImageRemoteRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class ImageRemoteRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient
) : ImageRemoteRepository {
    override suspend fun getImageByteArray(url: String): Either<DataError, ByteArray> {
        try {
            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                return response.body?.bytes()?.right() ?: DataError.IncorrectData.left()
            } else {
                return DataError.IncorrectData.left()
            }
        } catch (e: Exception) {
            Log.e("NetworkDataSource", "Error downloading image: ${e.message}", e)
            return DataError.ConnectionMissing.left()
        }
    }
}