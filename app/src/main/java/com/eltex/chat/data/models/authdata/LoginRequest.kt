package com.eltex.chat.data.models.authdata

data class LoginRequest(
    val user: String = "",
    val password: String = "",
)