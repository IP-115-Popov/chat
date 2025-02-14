package com.eltex.data.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserDTO>
)