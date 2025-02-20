package com.eltex.data.models.chatinfo

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val _id: String,
    val _updatedAt: String? = null,
    val lm: String? = null,
    val msgs: Int? = null,
    val t: String? = null,
    val ts: String? = null,
    val uids: List<String>? = null,
    val usernames: List<String>? = null,
    val fname: String? = null
)