package com.eltex.domain.models

import java.net.URI

data class MessagePayload(
    val roomId: String,
    val msg: String,
    val uri: String? = null,
)