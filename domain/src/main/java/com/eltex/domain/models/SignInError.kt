package com.eltex.domain.models

sealed interface SignInError {
    data object ConnectionMissing : SignInError
    data object Unauthorized : SignInError
}