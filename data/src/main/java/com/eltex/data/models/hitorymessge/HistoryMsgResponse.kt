package com.eltex.data.models.hitorymessge

import kotlinx.serialization.Serializable

@Serializable
data class HistoryMsgResponse(
    val messages: List<MessageDTO>,
    val success: Boolean
)