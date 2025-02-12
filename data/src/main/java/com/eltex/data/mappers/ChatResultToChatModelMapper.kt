package com.eltex.data.mappers

import com.eltex.data.models.chat.Result
import com.eltex.domain.models.ChatModel
import java.time.Instant

object ChatResultToChatModelMapper {
    fun map(result: Result) : ChatModel =  ChatModel(
        id = result._id,
        name = result.fname ?: "",
        lastMessage = result.lastMessage?.msg ?: "",
        lm= result.lm?.`$date`,//result.lm?.`$date`?.let {  Instant.ofEpochSecond(it)},
        unread=result.usersCount,
        avatarUrl=result.avatarETag,
    )
}