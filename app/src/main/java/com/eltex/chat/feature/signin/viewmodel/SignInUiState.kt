package com.eltex.chat.feature.signin.viewmodel

import com.eltex.chat.feature.signin.models.LoginUiModel

data class SignInUiState(
    val status: SignInStatus = SignInStatus.Idle,
    val user: LoginUiModel = LoginUiModel(),
)