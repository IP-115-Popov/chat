package com.eltex.data.models.profileinfo

import com.eltex.data.models.authdata.Email
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoRequest(
    val _id: String,
    val name: String,
    val status: String,
    val username: String,
    val avatarUrl: String = "",
    val active: Boolean,
    val emails: List<Email>,
    val statusConnection: String,
    val success: Boolean,
)