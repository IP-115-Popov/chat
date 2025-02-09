package com.eltex.chat.feature.authorization.models

sealed interface SignInError {
    data object ConnectionMissing : SignInError
    data object Unauthorized : SignInError
}