package com.eltex.chat.feature.infochat.viewmodel

import com.eltex.chat.feature.main.viewmodel.MainUiStatus

sealed interface ChatInfoStatus {
    data object Idle : ChatInfoStatus
    data object Loading : ChatInfoStatus
    data class Error(val errorMessage: String): ChatInfoStatus
}