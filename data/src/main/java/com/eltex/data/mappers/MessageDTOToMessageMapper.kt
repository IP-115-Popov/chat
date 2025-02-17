package com.eltex.data.mappers

import com.eltex.data.models.hitorymessge.MessageDTO
import com.eltex.data.util.parseDateStringToLong
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message

object MessageDTOToMessageMapper {
    fun map(messageDTO: MessageDTO): Message {
        val fileModel: FileModel? = messageDTO.attachments?.mapNotNull { jsonElement ->
            try {
                return@mapNotNull AttachmentsToFileModelMapper.map(jsonElement)
            } catch (e: Exception) {
                println("Error parsing attachment: ${e.message}")
                return@mapNotNull null
            }
        }?.firstOrNull()

        return Message(
            id = messageDTO._id,
            userId = messageDTO.u._id,
            msg = messageDTO.msg,
            date = parseDateStringToLong(messageDTO.ts) ?: 0L,
            name = messageDTO.u.name ?: messageDTO.u.username,
            fileModel = fileModel
        )
    }
}