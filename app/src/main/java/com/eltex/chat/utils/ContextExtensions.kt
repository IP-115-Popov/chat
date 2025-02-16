//package com.eltex.chat.utils
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import androidx.core.content.FileProvider
//import java.io.File
//
//fun Context.openFile(uri: String, mimeType: String): Boolean {
//    val file = File(this.cacheDir, getFileNameFromUri(uri))
//
//    if (!file.exists()) {
//        println("File not found locally: $uri")
//        return false
//    }
//
//    val fileUri: Uri = FileProvider.getUriForFile(
//        this,
//        "${this.packageName}.provider",
//        file
//    )
//
//    val intent = Intent(Intent.ACTION_VIEW).apply {
//        setDataAndType(fileUri, mimeType)
//        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
//    }
//
//    return try {
//        this.startActivity(intent)
//        true
//    } catch (e: Exception) {
//        println("No app found to open file: ${e.message}")
//        false
//    }
//}
//
//private fun getFileNameFromUri(uri: String): String {
//    return uri.substringAfterLast("/")
//}