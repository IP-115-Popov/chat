package com.eltex.data.mappers

import com.eltex.data.models.message.MessageResponse
import com.eltex.domain.models.Message

object MessageResponseToMessageMapper {
    fun map(messageResponse: MessageResponse) = with(messageResponse.args.first()) {
        Message(
            userId = u._id,
            name = u.name,
            date = _updatedAt.`$date`,
            msg = msg,
        )
    }
}