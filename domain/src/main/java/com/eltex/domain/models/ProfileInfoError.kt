package com.eltex.domain.models

sealed interface ProfileInfoError {
    data object ConnectionMissing : ProfileInfoError
    data object Unauthorized : ProfileInfoError
    data object LocalStorage : ProfileInfoError
}