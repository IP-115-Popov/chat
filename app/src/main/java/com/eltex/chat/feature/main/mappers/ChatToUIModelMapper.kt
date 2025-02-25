package com.eltex.chat.feature.main.mappers

import arrow.core.Either
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.remote.GetUserInfoUseCase
import javax.inject.Inject

class ChatToUIModelMapper @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) {
    suspend fun map(chatModel: ChatModel, userId: String?) = with(chatModel) {
        val name: String = if (chatModel.t == "d") {
            chatModel.uids?.firstOrNull { id -> id != userId } //
                ?.let { userId ->
                    when (val user = getUserInfoUseCase(userId)) {
                        is Either.Left -> chatModel.name ?: ""
                        is Either.Right -> user.value.name
                        else -> ""
                    }
                } ?: ""
        } else {
            chatModel.name ?: ""
        }

        val lastMessage = chatModel.message?.let { message ->
            getLastMessage(
                messsage = message, chatType = chatModel.t, userId
            )
        } ?: "Сообщений нет"

        ChatUIModel(
            id = chatModel.id,
            name = name,
            lastMessage = lastMessage,
            lm = chatModel.lm?.let { instant ->
                InstantFormatter.formatInstantToRelativeString(
                    instant
                )
            } ?: "",
            unread = chatModel.unread ?: 0,
            otrAck = "",
            avatarUrl = "",
            usernames = chatModel.usernames,
            t = chatModel.t,
        )
    }

    private fun getLastMessage(messsage: Message, chatType: String, userId: String?): String {
        var lastMessage = when (messsage.fileModel) {
            is FileModel.Document -> "Документ"
            is FileModel.Img -> "Изображение"
            is FileModel.Video -> "Видео"
            null -> if (messsage.msg.length > 0) messsage.msg else return "Сообщений нет"
        }

        if (chatType != "d") {
            val fio = when (messsage.userId) {
                userId -> "Вы"
                else -> messsage.name
            }
            lastMessage = fio + ":  " + lastMessage
        }
        return lastMessage
    }
}