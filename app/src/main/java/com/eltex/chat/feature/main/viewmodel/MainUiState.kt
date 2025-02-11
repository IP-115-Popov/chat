package com.eltex.chat.feature.main.viewmodel

import com.eltex.domain.repository.ChatDTO

data class MainUiState(
    val chatList: List<ChatDTO> = emptyList()
)