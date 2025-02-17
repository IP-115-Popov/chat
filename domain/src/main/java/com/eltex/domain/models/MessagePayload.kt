package com.eltex.domain.models

data class MessagePayload(
    val id: String,
    val roomId: String,
    val msg: String,
    val token: String,
    val fileModel: FileModel? = null,
)