package com.eltex.chat.feature.main.mappers

import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.models.UserModel

object ChatToUIModelMapper {
    fun map(chatModel: ChatModel, userId: String?, usersList: List<UserModel>) =
        with(chatModel) {
            val name: String = if (chatModel.t == "d") {
                chatModel.uids?.firstOrNull { uid -> uid != userId } //
                    ?.let { uid ->
                        usersList.firstOrNull { user -> user._id == uid }?.name
                    } ?: chatModel.name ?: ""
            } else {
                chatModel.name ?: ""
            }

            val lastMessage = chatModel.message?.let { message ->
                getLastMessage(
                    messsage = message, chatType = chatModel.t, userId
                )
            } ?: "Сообщений нет"

            val lastMessageDate = chatModel.lm?.let { instant ->
                InstantFormatter.formatInstantToRelativeString(
                    instant
                )
            } ?: message?.let {
                if (it.fileModel != null || it.msg.isNotBlank()) {
                    InstantFormatter.formatInstantToRelativeString(
                        it.date
                    )
                } else ""
            } ?: ""

            ChatUIModel(
                id = chatModel.id,
                name = name,
                lastMessage = lastMessage,
                lm = lastMessageDate,
                updatedAt = lm ?: updatedAt ?: Long.MAX_VALUE,
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