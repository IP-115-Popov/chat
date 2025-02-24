package com.eltex.chat.feature.userprofile.viewmodel

sealed interface UserProfileStatus {
    data object Idle : UserProfileStatus
    data object Loading : UserProfileStatus
    data class Error(val errorMessage: String) : UserProfileStatus
}