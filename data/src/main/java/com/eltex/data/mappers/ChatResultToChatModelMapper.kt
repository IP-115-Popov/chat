package com.eltex.data.mappers

import com.eltex.data.models.chat.Result
import com.eltex.domain.models.ChatModel

object ChatResultToChatModelMapper {
    fun map(result: Result): ChatModel = ChatModel(
        id = result._id,
        name = result.name ?: "",
        lastMessage = result.lastMessage?.msg ?: "",
        lm = result.lm?.`$date`,
        unread = result.usersCount,
        avatarUrl = result.avatarETag,
    )
}