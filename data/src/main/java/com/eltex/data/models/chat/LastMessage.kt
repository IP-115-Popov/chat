package com.eltex.data.models.chat

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class LastMessage(
    val _id: String,
    val _updatedAt: UpdatedAt,
    //val channels: List<@Contextual Any?>,
    //val md: List<Md>,
    //val mentions: List<@Contextual Any?>,
    val msg: String,
    //val rid: String,
    //val ts: TsX,
    //val u: UX,
    //val urls: List<@Contextual Any?>
)