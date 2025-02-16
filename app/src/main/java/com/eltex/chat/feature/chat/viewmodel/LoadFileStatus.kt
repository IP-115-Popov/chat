package com.eltex.chat.feature.chat.viewmodel

sealed interface LoadFileStatus {
    object IsLoaded: LoadFileStatus
    object IsLoading: LoadFileStatus
    object IsNotLoaded: LoadFileStatus
}