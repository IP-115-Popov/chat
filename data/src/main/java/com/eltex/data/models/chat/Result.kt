package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val _id: String,
    val fname: String? = null,
    val lastMessage: LastMessage? = null,
    val lm: Lm? = null,
    val usersCount: Int = 0,
    val avatarETag: String? = null,
)