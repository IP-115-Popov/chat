package com.eltex.data.models.chat

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class LastMessage(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val msg: String,
    val attachments: List<JsonElement>? = null,
    val u: U? = null,
)