package com.eltex.chat.feature.userprofile.viewmodel

import android.graphics.Bitmap
import com.eltex.domain.models.UserModel

data class UserProfileState (
    val status: UserProfileStatus = UserProfileStatus.Idle,
    val avatar: Bitmap? = null,
    val user: UserModel? = null,
)