package com.eltex.chat.feature.profile.viewmodel

import android.graphics.Bitmap
import com.eltex.chat.feature.profile.models.ProfileUiModel

data class ProfileState(
    val status: ProfileStatus = ProfileStatus.Idle,
    val profileUiModel: ProfileUiModel? = null,
    val avatarImg: Bitmap? = null,
    val token: String? = null,
)