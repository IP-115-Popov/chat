package com.eltex.data.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: UserDTO
)
