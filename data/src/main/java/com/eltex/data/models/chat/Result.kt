package com.eltex.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val _id: String,
    val t: String? = null,
    val fname: String? = null,
    val lastMessage: LastMessage? = null,
    val lm: Lm? = null,
    val _updatedAt: UpdatedAt? = null,
    val usersCount: Int = 0,
    val avatarETag: String? = null,
    val name: String? = null,
    val usernames: List<String>? = null,
    val uids: List<String>? = null,
)