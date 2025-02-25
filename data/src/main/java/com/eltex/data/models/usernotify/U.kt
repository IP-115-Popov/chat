package com.eltex.data.models.usernotify

import kotlinx.serialization.Serializable

@Serializable
data class U(
    val _id: String? = null,
    val name: String? = null,
    val username: String? = null
)