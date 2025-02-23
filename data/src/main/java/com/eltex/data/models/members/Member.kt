package com.eltex.data.models.members

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val _id: String,
    val name: String,
    val status: String,
    val username: String
)