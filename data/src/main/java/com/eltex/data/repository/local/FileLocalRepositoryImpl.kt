package com.eltex.data.repository.local

import android.content.Context
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.domain.models.DataError
import com.eltex.domain.repository.local.FileLocalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileLocalRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileLocalRepository {
    override suspend fun getFileData(uri: String): Either<DataError, ByteArray> {
        val file = File(context.filesDir, getFileName(uri))
        return if (file.exists()) {
            file.readBytes().right()
        } else {
            DataError.LocalStorage.left()
        }
    }

    override suspend fun saveFileData(uri: String, data: ByteArray) {
        val file = File(context.filesDir, getFileName(uri))
        file.writeBytes(data)
    }

    private fun getFileName(uri: String): String {
        return "file_${uri.hashCode()}"
    }
}