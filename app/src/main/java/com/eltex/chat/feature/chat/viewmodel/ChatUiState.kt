package com.eltex.chat.feature.chat.viewmodel

import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.domain.models.AuthData

data class ChatUiState(
    val status: ChatStatus = ChatStatus.Idle,
    val messages: List<MessageUiModel> = emptyList(),
    val authData: AuthData? = null,
)
