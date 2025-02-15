package com.eltex.data.models.hitorymessge

import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val _id: String,
    val _updatedAt: String,
    //val md: List<Md>,
    val msg: String,
    val rid: String,
    val ts: String,
    val u: U,
)