package com.eltex.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataEntity(
    val userId: String,
    val name: String,
    val authToken: String,
    val avatarUrl: String,
)