package com.eltex.data.models.members

import kotlinx.serialization.Serializable

@Serializable
data class MembersResponse(
    val members: List<Member>,
)