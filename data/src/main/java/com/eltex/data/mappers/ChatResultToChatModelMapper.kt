package com.eltex.data.mappers

import android.util.Log
import com.eltex.data.models.chat.Result
import com.eltex.domain.models.ChatModel
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message

object ChatResultToChatModelMapper {
    fun map(result: Result): ChatModel {
        val fileModel: FileModel? = try {
            result.lastMessage?.attachments?.mapNotNull { jsonElement ->
                try {
                    return@mapNotNull AttachmentsToFileModelMapper.map(jsonElement)
                } catch (e: Exception) {
                    println("Error parsing attachment: ${e.message}")
                    return@mapNotNull null
                }
            }?.firstOrNull()
        } catch (e: Exception) {
            Log.e("ChatResultToChatModelMapper", "get attachments error ${e.message}")
            null
        }
        return ChatModel(
            id = result._id,
            name = result.name,
            lm = result.lm?.`$date`,
            unread = result.usersCount,
            avatarUrl = result.avatarETag,
            usernames = if (result.t == "d") result.usernames else null,
            t = result.t ?: "",
            uids = if (result.t == "d") result.uids else null,
            message = Message(
                id = result.lastMessage?._id ?: "",
                rid = result._id,
                msg = result.lastMessage?.msg ?: "",
                date = result.lm?.`$date` ?: 0L,
                fileModel = fileModel,
                userId = result.lastMessage?.u?._id ?: "",
                name = result.lastMessage?.u?.name ?: result.lastMessage?.u?.username ?: "",
            )
        )
    }
}