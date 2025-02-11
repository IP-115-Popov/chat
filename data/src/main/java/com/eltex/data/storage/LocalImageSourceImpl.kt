package com.eltex.data.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocalImageSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalImageSource {
    override suspend fun getImageData(imageUrl: String): ByteArray? {
        val file = File(context.filesDir, getFileName(imageUrl))
        return if (file.exists()) {
            file.readBytes()
        } else {
            null
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