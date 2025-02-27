package com.eltex.chat.feature.main.models

import android.graphics.Bitmap

data class ChatUIModel(
    val id: String,
    val name: String,
    val lastMessage: String,
    val lm: String,
    val updatedAt: Long?,
    val unread: Int,
    val otrAck: String,
    val avatarUrl: String?,
    val avatar: Bitmap? = null,
    val usernames: List<String>?,
    val t: String,
)