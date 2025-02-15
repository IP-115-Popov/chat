package com.eltex.data.models.message

import kotlinx.serialization.Serializable

@Serializable
data class Arg(
    val _id: String,
    val _updatedAt: UpdatedAt,
    val md: List<Md>,
    val msg: String,
    val rid: String,
    val ts: Ts,
    val u: U,
)