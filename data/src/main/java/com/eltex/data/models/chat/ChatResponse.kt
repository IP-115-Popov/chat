package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id: String,
    val msg: String,
    val result: List<Result>
)