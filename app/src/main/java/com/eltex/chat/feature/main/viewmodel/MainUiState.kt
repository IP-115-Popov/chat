package com.eltex.chat.feature.main.viewmodel

data class MainUiState(
    val chatList: List<ChatUIModel> = emptyList(),
    val status: MainUiStatus = MainUiStatus.Idle
)