package com.eltex.data.models.lifemessage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Arg(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val md: List<Md>? = null,
    val msg: String,
    val rid: String,
    val ts: Ts,
    val u: U,
    @SerialName("attachments")
    val attachments: List<JsonElement>? = null,
)