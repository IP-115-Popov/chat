package com.eltex.data.models.message

import kotlinx.serialization.Serializable

@Serializable
data class U(
    val _id: String,
    val name: String,
    val username: String
)