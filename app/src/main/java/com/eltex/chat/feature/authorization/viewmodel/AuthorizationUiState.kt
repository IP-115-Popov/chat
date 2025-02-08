package com.eltex.chat.feature.authorization.viewmodel

import com.eltex.chat.feature.authorization.models.LoginUiModel

data class AuthorizationUiState(
    val status: AuthorizationStatus = AuthorizationStatus.Idle,
    val user: LoginUiModel = LoginUiModel(),
)