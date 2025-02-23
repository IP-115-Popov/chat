package com.eltex.chat.feature.infochat.models

import androidx.compose.ui.graphics.ImageBitmap

data class MemberUiModel(
    val id: String,
    val name: String,
    val avatar: ImageBitmap?,
    val status: String,
    val username: String,
)