package com.eltex.domain.models

data class Message(
    val msg: String,
    val date: Long,
    val userId: String,
    val name: String,
)