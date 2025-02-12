package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class LastMessage(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val msg: String,
)