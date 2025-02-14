package com.eltex.data.models.authdata

import kotlinx.serialization.Serializable

@Serializable
data class Me(
    val _id: String,
    val active: Boolean,
    val avatarUrl: String,
    val name: String,
    val username: String,
)