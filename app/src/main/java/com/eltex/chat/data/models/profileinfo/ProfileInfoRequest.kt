package com.eltex.chat.data.models.profileinfo

import com.eltex.chat.data.models.authdata.Email
import com.eltex.chat.data.models.authdata.Settings

data class ProfileInfoRequest(
    val _id: String,
    val active: Boolean,
    val avatarUrl: String,
    val customFields: CustomFields,
    val emails: List<Email>,
    val name: String,
    val roles: List<String>,
    val settings: Settings,
    val status: String,
    val statusConnection: String,
    val success: Boolean,
    val username: String,
    val utcOffset: Float,
)