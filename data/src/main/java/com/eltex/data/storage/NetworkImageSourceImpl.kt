package com.eltex.data.storage

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class NetworkImageSourceImpl @Inject constructor(private val okHttpClient: OkHttpClient) :
    NetworkImageSource {
    override suspend fun getImageData(imageUrl: String): ByteArray? {
        try {
            val request = Request.Builder().url(imageUrl).build()
            val response = okHttpClient.newCall(request).execute()

            return if (response.isSuccessful) {
                response.body?.bytes() ?: null
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("NetworkDataSource", "Error downloading image: ${e.message}", e)
            return null
        }
    }
}