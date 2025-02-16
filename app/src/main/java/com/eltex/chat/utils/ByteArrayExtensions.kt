package com.eltex.chat.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun ByteArray.byteArrayToBitmap(): Bitmap? {
    return try {
        BitmapFactory.decodeByteArray(this, 0, this.size)
    } catch (e: Exception) {
        // Handle potential out-of-memory errors or invalid image format
        println("Error decoding ByteArray to Bitmap: ${e.message}")
        null
    }
}