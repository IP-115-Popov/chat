package com.eltex.data.models.lifemessage

import kotlinx.serialization.Serializable

@Serializable
data class Value(
    val type: String,
    val value: String
)