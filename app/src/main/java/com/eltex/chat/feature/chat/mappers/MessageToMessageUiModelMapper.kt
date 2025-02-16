package com.eltex.chat.feature.chat.mappers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import arrow.core.Either
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.GetImageUseCase
import javax.inject.Inject


class MessageToMessageUiModelMapper @Inject constructor(
    private val getImageUseCase: GetImageUseCase
) {

    suspend fun map(message: Message): MessageUiModel = with(message) {

        val bitmap = when(val file = message.fileModel) {
            is FileModel.Img -> {
                val byteArray = getImageUseCase.execute("https://eltex2025.rocket.chat"+file.uri)
                when(byteArray) {
                    is Either.Left -> null
                    is Either.Right -> {
                        byteArrayToBitmap(byteArray.value)
                    }
                    else -> null
                }
            }
            is FileModel.Document,
            is FileModel.Video,
            null -> null
        }
        MessageUiModel(
            msg = msg,
            date = date,
            userId = userId,
            username = name,
            fileModel = fileModel,
            bitmap = bitmap,
        )
    }
    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return try {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            // Handle potential out-of-memory errors or invalid image format
            println("Error decoding ByteArray to Bitmap: ${e.message}")
            null
        }
    }
}