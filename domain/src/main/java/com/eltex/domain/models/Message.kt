package com.eltex.domain.models

data class Message(
    val id: String,
    val msg: String,
    val date: Long,
    val userId: String,
    val name: String,
    val fileModel: FileModel? = null,
)