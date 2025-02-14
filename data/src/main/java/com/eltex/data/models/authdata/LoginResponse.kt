package com.eltex.data.models.authdata

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val `data`: Data,
)