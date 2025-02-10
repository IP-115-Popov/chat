package com.eltex.chat.feature.signin.viewmodel

sealed interface SignInStatus {
    data object Idle : SignInStatus
    data object Loading : SignInStatus
    data object SignInSuccessful : SignInStatus
    data class Error(val message: String) : SignInStatus
}