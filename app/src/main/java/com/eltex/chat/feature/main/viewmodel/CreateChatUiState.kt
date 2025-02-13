package com.eltex.chat.feature.main.viewmodel

import com.eltex.chat.feature.main.models.UserUiModel

data class CreateChatUiState(
    val userList: List<UserUiModel> = emptyList(),
    val status: CreateChatStatus = CreateChatStatus.Idle,
    val searchValue: String = "",
)