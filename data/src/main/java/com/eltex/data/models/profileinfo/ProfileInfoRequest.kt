package com.eltex.data.models.profileinfo

import com.eltex.data.models.authdata.Email
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoRequest(
    val _id: String,
    val active: Boolean,
    val avatarUrl: String,
    val customFields: CustomFields,
    val emails: List<Email>,
    val name: String,
    val roles: List<String>,
    val status: String,
    val statusConnection: String,
    val success: Boolean,
    val username: String,
    val utcOffset: Float,
)