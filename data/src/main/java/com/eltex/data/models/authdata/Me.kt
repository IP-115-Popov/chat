package com.eltex.data.models.authdata

data class Me(
    val _id: String,
    val active: Boolean,
    val avatarUrl: String,
    val emails: List<Email>,
    val name: String,
    val roles: List<String>,
    val settings: Settings,
    val status: String,
    val statusConnection: String,
    val username: String,
    val utcOffset: Float,
)