package com.eltex.data.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val _id: String,
    val active: Boolean,
    val name: String,
    val nameInsensitive: String,
    val status: String,
    val username: String
)