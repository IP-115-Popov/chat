package com.eltex.chat.feature.chat.viewmodel

import android.net.Uri
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.domain.models.AuthData

data class ChatUiState(
    val status: ChatStatus = ChatStatus.Idle,
    val messages: List<MessageUiModel> = emptyList(),
    val authData: AuthData? = null,
    val roomType: String? = null,
    val roomId: String? = null,
    val offset: Int = 0,
    val isAtEnd: Boolean = false,
    val msgText: String = "",
    val attachmentUriList: Set<Uri> = emptySet(),
)
