package com.eltex.data.models.hitorymessge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MessageDTO(
    val _id: String,
    val _updatedAt: String,
    val msg: String,
    val rid: String,
    val ts: String,
    val u: U,
    @SerialName("attachments")
    val attachments: List<JsonElement>? = null,
)