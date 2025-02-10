package com.eltex.chat.feature.authorization.models

data class AuthData(
    val userId: String,
    val name: String,
    val authToken: String,
    val avatarUrl: String
)
