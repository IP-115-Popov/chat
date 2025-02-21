package com.eltex.chat.feature.createchat.model

import android.graphics.Bitmap

data class UserUiModel(
    val _id: String,
    val active: Boolean,
    val name: String,
    val username: String,
    val status: String,
    val avatar: Bitmap?,
)