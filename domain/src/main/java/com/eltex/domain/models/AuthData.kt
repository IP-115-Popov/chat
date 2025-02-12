package com.eltex.domain.models

data class AuthData(
    val userId: String,
    val name: String,
    val authToken: String,
    val avatarUrl: String,
)
