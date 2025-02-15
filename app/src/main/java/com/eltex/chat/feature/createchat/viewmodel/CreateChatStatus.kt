package com.eltex.chat.feature.createchat.viewmodel

sealed interface CreateChatStatus {
    data object Idle : CreateChatStatus
    data object Loading : CreateChatStatus
    data class Error(val errorMessage: String) : CreateChatStatus
}