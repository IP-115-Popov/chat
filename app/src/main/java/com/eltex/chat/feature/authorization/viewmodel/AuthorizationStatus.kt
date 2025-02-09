package com.eltex.chat.feature.authorization.viewmodel

sealed interface AuthorizationStatus {
    data object Idle : AuthorizationStatus
    data object Loading : AuthorizationStatus
    data object AuthorizationSuccessful : AuthorizationStatus
    data class Error(val message: String) : AuthorizationStatus
}