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

//    val _updatedAt: UpdatedAt,
//    val announcement: String,
//    val broadcast: Boolean,
//    val default: Boolean,
//    val description: String,
//    val encrypted: Boolean,
//    val msgs: Int,
//    val name: String,
//    val ro: Boolean,
//    val sysMes: Boolean,
//    val t: String,
//    val topic: String,
//    val ts: TsX,
//    val u: UX
)