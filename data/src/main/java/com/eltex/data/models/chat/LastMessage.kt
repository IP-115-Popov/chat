package com.eltex.data.models.chat

data class LastMessage(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val channels: List<Any?>,
    val md: List<Md>,
    val mentions: List<Any?>,
    val msg: String,
    val rid: String,
    val ts: TsX,
    val u: UX,
    val urls: List<Any?>
)