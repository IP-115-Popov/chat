package com.eltex.chat.data.models.authdata

data class Data(
    val authToken: String,
    val me: Me,
    val userId: String
)