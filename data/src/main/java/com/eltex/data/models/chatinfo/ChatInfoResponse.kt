package com.eltex.data.models.chatinfo

import kotlinx.serialization.Serializable

@Serializable
data class ChatInfoResponse(
    val room: Room,
)