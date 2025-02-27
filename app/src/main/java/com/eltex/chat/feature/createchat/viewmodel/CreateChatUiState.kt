package com.eltex.chat.feature.createchat.viewmodel

import com.eltex.chat.feature.createchat.model.UserUiModel

data class CreateChatUiState(
    val userList: List<UserUiModel> = emptyList(),
    val status: CreateChatStatus = CreateChatStatus.Idle,
    val searchValue: String = "",
    val userId: String? = null,
)