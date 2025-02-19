package com.eltex.domain.models


data class ChatModel(
    val id: String,
    val name: String? = null,
    //val lastMessage: String? = null,
    val lm: Long? = null, //Временная метка последнего сообщения.
    val unread: Int? = null, //Количество непрочитанных сообщений в комнате.
    val avatarUrl: String? = null,
    val usernames: List<String>? = null,
    val t: String,
    //val fileModel: FileModel? = null,
    val message: Message? =null,
)