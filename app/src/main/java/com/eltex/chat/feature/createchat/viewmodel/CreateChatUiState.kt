package com.eltex.chat.feature.createchat.viewmodel

import com.eltex.chat.feature.createchat.model.UserUiModel
import com.eltex.domain.models.AuthData

data class CreateChatUiState(
    val userList: List<UserUiModel> = emptyList(),
    val status: CreateChatStatus = CreateChatStatus.Idle,
    val searchValue: String = "",
    val authData: AuthData? = null,
)