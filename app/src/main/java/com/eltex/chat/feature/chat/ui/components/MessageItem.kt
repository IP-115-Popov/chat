package com.eltex.chat.feature.chat.ui.components

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.eltex.domain.models.FileModel

@Composable
fun MessageItem(
    title: String,
    text: String,
    time: String,
    bitmap: Bitmap? = null,
    fileModel: FileModel? = null,
) {
    if (text.length > 33 || fileModel != null || bitmap != null) {
        BigMessageItem(
            title = title,
            text = text,
            time = time,
            bitmap = bitmap,
            fileModel = fileModel,
        )
    }
    else {
        LittleMessageItem(
            title = title,
            text = text,
            time = time,
        )
    }
}