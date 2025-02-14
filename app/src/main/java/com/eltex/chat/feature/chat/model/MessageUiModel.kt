package com.eltex.chat.feature.chat.model

data class MessageUiModel(
    val msg: String,
    val date: Long,
    val userId: String,
    val name: String,
)
