package com.eltex.chat.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.openFile(uri: String, mimeType: String): Boolean {
    val originalFile = File(this.filesDir, getFileName(uri))

    return if (originalFile.exists()) {
        val cacheFile = File(this.cacheDir, originalFile.name)
        println("Cache dir ${cacheDir}")

        return try {
            // Копируем файл из private storage в cache directory
            originalFile.copyTo(cacheFile, overwrite = true)

            val fileUri: Uri = FileProvider.getUriForFile(
                this,
                "${this.packageName}.provider",
                cacheFile
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(fileUri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            startActivity(intent)
            true
        } catch (e: Exception) {
            println("Error opening file: ${e.message}")
            e.printStackTrace()
            false
        }
    } else {
        println("File not found: ${originalFile.absolutePath}")
        false
    }
}

private fun getFileName(uri: String): String {
    return "file_${uri.hashCode()}"
}