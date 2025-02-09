package com.eltex.chat.feature.profile.viewmodel

import com.eltex.chat.models.User

data class ProfileState(
    val status: ProfileStatus = ProfileStatus.Idle,
    val user: User? = null,
)