package com.eltex.data.storage

interface NetworkImageSource {
    suspend fun getImageData(imageUrl: String): ByteArray?
}