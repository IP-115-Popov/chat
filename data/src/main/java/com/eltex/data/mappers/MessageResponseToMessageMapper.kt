package com.eltex.data.mappers

import com.eltex.data.models.lifemessage.MessageResponse
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message

object MessageResponseToMessageMapper {
    fun map(messageResponse: MessageResponse) = with(messageResponse.args.first()) {
        val fileModel: FileModel? =
            messageResponse.args.first().attachments?.mapNotNull { jsonElement ->
                try {
                    return@mapNotNull AttachmentsToFileModelMapper.map(jsonElement)
                } catch (e: Exception) {
                    println("Error parsing attachment: ${e.message}")
                    return@mapNotNull null
                }
            }?.firstOrNull()

        Message(
            id = _id,
            rid = rid,
            userId = u._id,
            name = u.name,
            date = _updatedAt.`$date`,
            msg = msg,
            fileModel = fileModel,
        )
    }
}