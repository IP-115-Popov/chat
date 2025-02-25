package com.eltex.data.models.usernotify

import kotlinx.serialization.Serializable

@Serializable
data class RoomInserted(
    val _id: String,
    val fname: String? = null,
    val _USERNAMES: List<String>? = null,
    val msgs: Int? = null,
    val t: String,
    val uids: List<String>? = null,
    val usernames: List<String>? = null,
    val usersCount: Int? = null,
    val lastMessage: LastMessageUserNotify? = null
)