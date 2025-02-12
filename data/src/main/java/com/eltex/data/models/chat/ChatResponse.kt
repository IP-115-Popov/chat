package com.eltex.data.models.chat

data class ChatResponse(
    val id: String,
    val msg: String,
    val result: List<Result>
)