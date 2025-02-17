package com.eltex.data.models.lifemessage

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val args: List<Arg>,
    val eventName: String
)