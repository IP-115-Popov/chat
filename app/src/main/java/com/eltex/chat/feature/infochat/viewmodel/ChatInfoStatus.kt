package com.eltex.chat.feature.infochat.viewmodel

sealed interface ChatInfoStatus {
    data object Idle : ChatInfoStatus
    data object Loading : ChatInfoStatus
    data class Error(val errorMessage: String) : ChatInfoStatus
}