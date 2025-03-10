package com.eltex.chat.feature.chat.model

import android.graphics.Bitmap
import com.eltex.domain.models.FileModel

data class MessageUiModel(
    val id: String,
    val msg: String,
    val date: Long,
    val userId: String,
    val name: String,
    val username: String?,
    val fileModel: FileModel? = null,
    val bitmap: Bitmap? = null
)
