package com.eltex.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.data.storage.LocalImageSource
import com.eltex.data.storage.NetworkImageSource
import com.eltex.domain.feature.profile.repository.ImageRepository
import com.eltex.domain.models.DataError
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkImageSource,
    private val localImageSource: LocalImageSource
) : ImageRepository {

    override suspend fun getImage(imageUrl: String): Either<DataError, ByteArray> {
        return try {
            val networkResult = networkDataSource.getImageData(imageUrl)
            if (networkResult != null) {
                localImageSource.saveImageData(imageUrl, networkResult)
                networkResult.right()
            } else {
                val img = localImageSource.getImageData(imageUrl)
                if (img != null) img.right()
                else DataError.LocalStorage.left()
            }
        } catch (e: Exception) {
            Log.e("ImageRepository", "Error loading from network", e)
            try {
                localImageSource.getImageData(imageUrl)?.right() ?: DataError.DefaultError.left()
            } catch (localException: Exception) {
                Log.e("ImageRepository", "Error loading from local", localException)
                DataError.DefaultError.left()
            }
        }
    }
}
