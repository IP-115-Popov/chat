package com.eltex.chat.feature.chat.viewmodel

sealed interface ChatStatus {
    object Idle: ChatStatus
    object Loading: ChatStatus
    object NextPageLoading: ChatStatus
    object Error: ChatStatus
}