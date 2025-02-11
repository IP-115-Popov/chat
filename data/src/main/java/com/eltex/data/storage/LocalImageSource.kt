package com.eltex.data.storage

interface LocalImageSource {
    suspend fun getImageData(imageUrl: String): ByteArray?
    suspend fun saveImageData(imageUrl: String, data: ByteArray)
}