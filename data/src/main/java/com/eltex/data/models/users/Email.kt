package com.eltex.data.models.users

import kotlinx.serialization.Serializable

@Serializable
data class Email(
    val address: String,
    val verified: Boolean
)