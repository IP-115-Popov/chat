package com.eltex.chat.feature.profile.viewmodel

import com.eltex.chat.models.AuthData

data class ProfileState(
    val status: ProfileStatus = ProfileStatus.Idle,
    val authData: AuthData? = null,
)