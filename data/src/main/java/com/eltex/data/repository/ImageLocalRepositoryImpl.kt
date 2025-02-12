package com.eltex.data.repository

import android.content.Context
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.ImageLocalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ImageLocalRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageLocalRepository {
    override suspend fun getImageData(imageUrl: String): Either<DataError, ByteArray> {
        val file = File(context.filesDir, getFileName(imageUrl))
        return if (file.exists()) {
            file.readBytes().right()
        } else {
            DataError.LocalStorage.left()
        }
    }

    override suspend fun saveImageData(imageUrl: String, data: ByteArray) {
        val file = File(context.filesDir, getFileName(imageUrl))
        file.writeBytes(data)
    }

    private fun getFileName(imageUrl: String): String {
        return "image_${imageUrl.hashCode()}.jpg"
    }
}