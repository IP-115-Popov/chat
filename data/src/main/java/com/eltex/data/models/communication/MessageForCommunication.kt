package com.eltex.data.models.communication

import kotlinx.serialization.Serializable

@Serializable
data class MessageForCommunication(
    val msg: String,
    val rid: String,
)
