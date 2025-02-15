package com.eltex.data.models.hitorymessge

import kotlinx.serialization.Serializable

@Serializable
data class U(
    val _id: String,
    val name: String? = null,
    val username: String
)