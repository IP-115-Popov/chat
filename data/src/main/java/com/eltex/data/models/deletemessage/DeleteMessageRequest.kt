package com.eltex.data.models.deletemessage

import kotlinx.serialization.Serializable

@Serializable
data class DeleteMessageRequest(
    val msgId: String,
    val roomId: String
)