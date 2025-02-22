package com.eltex.domain.models


data class ChatModel(
    val id: String,
    val name: String? = null,
    val lm: Long? = null,
    val unread: Int? = null,
    val avatarUrl: String? = null,
    val usernames: List<String>? = null,
    val uids: List<String>? = null,
    val t: String,
    val message: Message? = null,
    val usersCount: Int? = null,
)