package com.eltex.domain.models

data class Message(
    val id: String,
    val rid: String,
    val msg: String,
    val date: Long,
    val userId: String,
    val name: String,
    val username: String?,
    val fileModel: FileModel? = null,
)