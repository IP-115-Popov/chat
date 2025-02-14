package com.eltex.chat.feature.chat.viewmodel

import com.eltex.chat.feature.chat.model.MessageUiModel

data class ChatUiState(
    val status: ChatStatus = ChatStatus.Idle,
    val messages: List<MessageUiModel> = emptyList(),
)
