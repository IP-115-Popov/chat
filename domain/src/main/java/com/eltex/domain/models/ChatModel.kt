package com.eltex.domain.models

data class ChatModel(
    val id: String,
    val name: String,
    val lastMessage: String,
    val lm: String, //Временная метка последнего сообщения.
    val unread: Int, //Количество непрочитанных сообщений в комнате.
    val avatarUrl: String?,
)