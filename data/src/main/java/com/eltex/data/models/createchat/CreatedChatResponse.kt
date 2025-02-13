package com.eltex.data.models.createchat

import kotlinx.serialization.Serializable

@Serializable
data class CreatedChatResponse(
    val room: Room,
    val success: Boolean
)