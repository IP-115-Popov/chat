package com.eltex.chat.feature.chat.mappers

import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.domain.models.Message

object MessageToMessageUiModelMapper {
    fun map(message: Message): MessageUiModel = with(message){
        MessageUiModel(
            msg = msg,
            date = date,
            userId = userId,
            name = name,
        )
    }
}