package com.eltex.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.api.ImageApi
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.ImageRemoteRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class ImageRemoteRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
) : ImageRemoteRepository {
    override suspend fun getImageByteArray(url: String): Either<DataError, ByteArray> {
        return try {
            val responseBody = imageApi.getImage(url)
            val byteArray = responseBody.bytes()
            byteArray.right()

        } catch (e: Exception) {
            Log.e("NetworkDataSource", "Ошибка загрузки изображения: ${e.message}", e)
            DataError.ConnectionMissing.left() // Failure: Connection issue or other error
        }
    }
}