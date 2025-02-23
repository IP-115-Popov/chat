package com.eltex.chat.feature.infochat.viewmodel

import com.eltex.chat.feature.createchat.model.UserUiModel

data class ChatInfoState(
    val status: ChatInfoStatus = ChatInfoStatus.Idle,
    val membersList: List<UserUiModel> = emptyList()
)