package com.eltex.domain.models

data class UserModel(
    val _id: String,
    val active: Boolean,
    val name: String,
    val nameInsensitive: String?,
    val status: String,
    val username: String,
)
