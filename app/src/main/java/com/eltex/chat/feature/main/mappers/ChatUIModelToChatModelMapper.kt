package com.eltex.chat.feature.main.mappers

import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.domain.models.ChatModel

object ChatUIModelToChatModelMapper {
    fun map(chatUIModel: ChatUIModel): ChatModel = with(chatUIModel) {
        ChatModel(
            id = id,
            name = name,
            unread = unread ?: 0,
            avatarUrl = "",
            usernames = usernames,
            t = t,
        )
    }
}