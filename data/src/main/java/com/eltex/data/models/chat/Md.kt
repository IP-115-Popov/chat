package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class Md(
    val type: String,
    val value: List<Value>
)