package com.eltex.chat.feature.chat.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.ProfileModel

data class ChatUiState(
    val status: ChatStatus = ChatStatus.Idle,
    val name: String? = null,
    val avatar: ImageBitmap? = null,
    val messages: List<MessageUiModel> = emptyList(),
    val profileModel: ProfileModel? = null,
    val roomType: String? = null,
    val roomId: String? = null,
    val offset: Int = 0,
    val isAtEnd: Boolean = false,
    val msgText: String = "",
    val attachmentUriList: List<Uri> = emptyList(),
    val chatModel: ChatModel? = null,
    val usernameToAvatarsMap: Map<String, ImageBitmap> = mutableMapOf(),
    val recipientUserId: String? = null,
    val selectedMessages: List<MessageUiModel> = emptyList(),
    val canDeleteMsg: Boolean = false,
)
