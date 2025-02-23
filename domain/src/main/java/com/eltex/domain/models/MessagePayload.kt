package com.eltex.domain.models

data class MessagePayload(
    val roomId: String,
    val msg: String,
    val uri: String? = null,
)