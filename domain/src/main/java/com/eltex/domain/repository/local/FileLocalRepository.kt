package com.eltex.domain.repository.local

import arrow.core.Either
import com.eltex.domain.models.DataError

interface FileLocalRepository {
    suspend fun getFileData(uri: String): Either<DataError, ByteArray>
    suspend fun saveFileData(uri: String, data: ByteArray)
    suspend fun deleteAllFiles(): Boolean
}