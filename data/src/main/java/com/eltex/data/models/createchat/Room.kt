package com.eltex.data.models.createchat

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val _id: String,
    val rid: String,
    val t: String,
    val usernames: List<String>
)