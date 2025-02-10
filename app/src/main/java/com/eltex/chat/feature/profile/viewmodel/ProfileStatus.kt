package com.eltex.chat.feature.profile.viewmodel

sealed interface ProfileStatus {
    data object Idle : ProfileStatus
    data object Loading : ProfileStatus
    data class Error(val errorMessage: String) : ProfileStatus
}