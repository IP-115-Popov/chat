package com.eltex.data.models.hitorymessge

import kotlinx.serialization.Serializable

@Serializable
data class Md(
    val type: String,
    val value: List<Value>
)