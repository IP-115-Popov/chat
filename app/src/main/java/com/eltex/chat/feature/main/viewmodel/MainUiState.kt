package com.eltex.chat.feature.main.viewmodel

import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.main.models.UserUiModel
import com.eltex.chat.feature.profile.models.ProfileUiModel

data class MainUiState(
    val chatList: List<ChatUIModel> = emptyList(),
    val userList: List<UserUiModel> = emptyList(),
    val status: MainUiStatus = MainUiStatus.Idle,
    val profileUiModel: ProfileUiModel? = null,
)