package com.eltex.data.models.message

import kotlinx.serialization.Serializable

@Serializable
data class Value(
    val type: String,
    val value: String
)