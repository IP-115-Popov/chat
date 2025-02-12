package com.eltex.chat.feature.main.viewmodel

import com.eltex.chat.feature.main.models.ChatUIModel

data class MainUiState(
    val chatList: List<ChatUIModel> = emptyList(),
    val status: MainUiStatus = MainUiStatus.Idle
)