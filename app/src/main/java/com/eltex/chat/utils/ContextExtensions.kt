package com.eltex.chat.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.eltex.domain.models.FileModel
import com.eltex.domain.Сonstants
import java.io.File

fun Context.openFile(fileModel: FileModel) {
    when(val file = fileModel){
        is FileModel.Document -> {
            val originalFile = File(this.filesDir, getFileName(file.uri))

            if (originalFile.exists()) {
                val cacheFile = File(this.cacheDir, originalFile.name)

                try {
                    // Копируем файл из private storage в cache directory
                    originalFile.copyTo(cacheFile, overwrite = true)

                    val fileUri: Uri = FileProvider.getUriForFile(
                        this,
                        "${this.packageName}.provider",
                        cacheFile
                    )
                    val type: String? = when(file.format){
                        "PDF" -> "application/pdf"
                        "TXT" -> "text/plain"
                        "DOC" -> "application/msword"
                        "DOCX" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                        "XLS" -> "application/vnd.ms-excel"
                        "XLSX" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        "PPT" -> "application/vnd.ms-powerpoint"
                        "PPTX" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                        "JPG", "JPEG" -> "image/jpeg"
                        "PNG" -> "image/png"
                        "GIF" -> "image/gif"
                        "BMP" -> "image/bmp"
                        "WEBP" -> "image/webp"
                        "MP4" -> "video/mp4"
                        "AVI" -> "video/x-msvideo"
                        "MOV" -> "video/quicktime"
                        "MKV" -> "video/x-matroska"
                        "ZIP" -> "application/zip"
                        "RAR" -> "application/x-rar-compressed"
                        "CSV" -> "text/csv"
                        "RTF" -> "application/rtf"
                        "ODT" -> "application/vnd.oasis.opendocument.text"
                        "ODS" -> "application/vnd.oasis.opendocument.spreadsheet"
                        "ODP" -> "application/vnd.oasis.opendocument.presentation"
                        "SVG" -> "image/svg+xml"
                        "MP3" -> "audio/mpeg"
                        "WAV" -> "audio/wav"
                        else -> null
                    }
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(fileUri, type)
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Log.e("OpenFileError", "No Activity found to handle file, attempting to move to Downloads", e)
                    }
                } catch (e: Exception) {
                    Log.e("OpenFileError", "Error opening file: ${e.message}", e)
                    e.printStackTrace()
                    Toast.makeText(this, "Ошибка при открытии файла", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("OpenFileError", "File not found: ${originalFile.absolutePath}")
                Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show()
            }
        }
        is FileModel.Video -> {
          val uri = Сonstants.BASE_URL + file.uri
            val videoUri = Uri.parse(uri)
            val browserIntent = Intent(Intent.ACTION_VIEW, videoUri)

            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                Log.e("OpenFileError", "No browser found to open video link", e)
                Toast.makeText(this, "Не найден браузер для открытия ссылки", Toast.LENGTH_SHORT).show()
            }
        }

        is FileModel.Img -> {}
    }
}


private fun getFileName(uri: String): String {
    return "file_${uri.hashCode()}"
}