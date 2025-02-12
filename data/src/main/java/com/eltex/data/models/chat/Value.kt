package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class Value(
    val type: String,
    val value: String? = null,
)
