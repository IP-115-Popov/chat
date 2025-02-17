package com.eltex.data.models.communication

import kotlinx.serialization.Serializable

@Serializable
data class TextMessagePayload(
    val message: MessageForCommunication,
)