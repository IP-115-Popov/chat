package com.eltex.data.models.authdata

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val authToken: String,
    val me: Me,
    val userId: String
)