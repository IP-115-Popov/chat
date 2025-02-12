package com.eltex.data.models.chat

data class Result(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val announcement: String,
    val avatarETag: String,
    val broadcast: Boolean,
    val default: Boolean,
    val description: String,
    val encrypted: Boolean,
    val fname: String,
    val lastMessage: LastMessage,
    val lm: Lm,
    val msgs: Int,
    val name: String,
    val ro: Boolean,
    val sysMes: Boolean,
    val t: String,
    val topic: String,
    val ts: TsX,
    val u: UX,
    val usernames: List<Any?>,
    val usersCount: Int
)