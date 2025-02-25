package com.eltex.data.models.usernotify

import kotlinx.serialization.Serializable

@Serializable
data class LastMessageUserNotify(
    val _id: String,
    val _updatedAt: UpdatedAt? = null,
    val msg: String? = null,
    val rid: String,
    val u: U? = null,
)