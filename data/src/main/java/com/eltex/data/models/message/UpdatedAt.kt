package com.eltex.data.models.message

import kotlinx.serialization.Serializable

@Serializable
data class UpdatedAt(
    val `$date`: Long
)