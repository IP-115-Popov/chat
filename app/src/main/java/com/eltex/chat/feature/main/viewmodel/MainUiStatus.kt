package com.eltex.chat.feature.main.viewmodel

sealed interface MainUiStatus {
    data object Idle : MainUiStatus
    data object Loading : MainUiStatus
    data class Error(val errorMessage: String) : MainUiStatus
}