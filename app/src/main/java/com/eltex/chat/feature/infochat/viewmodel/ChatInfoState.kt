package com.eltex.chat.feature.infochat.viewmodel

import android.graphics.Bitmap
import com.eltex.chat.feature.infochat.models.MemberUiModel
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.ProfileModel

data class ChatInfoState(
    val status: ChatInfoStatus = ChatInfoStatus.Idle,
    val avatar: Bitmap? = null,
    val membersList: List<MemberUiModel> = emptyList(),
    val chatModel: ChatModel? = null,
    val roomId: String? = null,
    val roomType: String? = null,
    val profileModel: ProfileModel? = null,
)