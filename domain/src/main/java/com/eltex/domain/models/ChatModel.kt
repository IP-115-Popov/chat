package com.eltex.domain.models

import java.time.Instant


data class ChatModel(
    val id: String,
    val name: String,
    val lastMessage: String,
    val lm: Instant?, //Временная метка последнего сообщения.
    val unread: Int, //Количество непрочитанных сообщений в комнате.
    val avatarUrl: String?,
)