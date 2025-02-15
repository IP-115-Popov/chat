package com.eltex.chat.feature.main.models

data class ChatUIModel(
    val id: String,
    val name: String,
    val lastMessage: String,
    val lm: String, //Временная метка последнего сообщения.
    val unread: Int, //Количество непрочитанных сообщений в комнате.
    val otrAck: String, //Статус подтверждения получения неофициального сообщения.
    val avatarUrl: String?,
    val usernames: List<String>?,
    val t: String,
)