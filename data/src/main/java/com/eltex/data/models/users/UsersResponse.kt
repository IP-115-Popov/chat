package com.eltex.data.models.users

import com.eltex.data.models.profileinfo.ProfileInfoRequest
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserDTO>
)