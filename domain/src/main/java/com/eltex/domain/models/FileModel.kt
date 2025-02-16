package com.eltex.domain.models

sealed class FileModel {
    data class Img(
        val uri: String,
        val format: String?,
    ) : FileModel()

    data class Video(
        val uri: String,
        val format: String?,
    ) : FileModel()

    data class Document(
        val uri: String,
        val type: String,
        val format: String?,
        val title: String?,
    ) : FileModel()
}