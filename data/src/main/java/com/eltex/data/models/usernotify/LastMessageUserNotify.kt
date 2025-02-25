package com.eltex.data.models.usernotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class LastMessageUserNotify(
    val _id: String,
    val _updatedAt: UpdatedAt? = null,
    val msg: String? = null,
    val rid: String,
    val u: U? = null,
    @SerialName("attachments")
    val attachments: List<JsonElement>? = null,
)