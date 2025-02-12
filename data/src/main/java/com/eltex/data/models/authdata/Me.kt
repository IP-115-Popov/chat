package com.eltex.data.models.authdata

import kotlinx.serialization.Serializable

@Serializable
data class Me(
    val _id: String,
    val active: Boolean,
    val avatarUrl: String,
    val emails: List<Email>,
    val name: String,
    val status: String,
    val statusConnection: String,
    val username: String,
)