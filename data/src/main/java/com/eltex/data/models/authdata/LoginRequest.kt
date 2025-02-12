package com.eltex.data.models.authdata

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val user: String = "",
    val password: String = "",
)