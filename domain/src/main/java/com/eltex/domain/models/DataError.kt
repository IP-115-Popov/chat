package com.eltex.domain.models

sealed interface DataError {
    data object ConnectionMissing : DataError
    data object IncorrectData : DataError
    data object LocalStorage : DataError
    data object DefaultError : DataError
}