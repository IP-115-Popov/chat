package com.eltex.data.mappers

import com.eltex.data.models.hitorymessge.MessageDTO
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object MessageDTOToMessageMapper {
    fun map(messageDTO: MessageDTO): Message {
        val fileModel: FileModel? = messageDTO.attachments?.mapNotNull { jsonElement ->
            try {
                val jsonObject = jsonElement.jsonObject
                if (jsonObject.containsKey("type") && jsonObject.containsKey("title_link")) {
                    val type = jsonObject["type"]?.jsonPrimitive?.content
                    val uri = jsonObject["title_link"]?.jsonPrimitive?.content

                    if (type == null || uri == null) return@mapNotNull null

                    jsonObject["image_type"]?.jsonPrimitive?.content?.let { image_type ->
                        return@mapNotNull FileModel.Img(
                             uri = uri,
                             format = image_type,
                        )
                    }

                    jsonObject["video_type"]?.jsonPrimitive?.content?.let { video_type ->
                        return@mapNotNull FileModel.Video(
                            uri = uri,
                            format = video_type,
                        )
                    }

                    jsonObject["video_type"]?.jsonPrimitive?.content?.let { video_type ->
                        return@mapNotNull FileModel.Video(
                            uri = uri,
                            format = video_type,
                        )
                    }




                    if (jsonObject.containsKey("format")) {
                        val format = jsonObject["format"]?.jsonPrimitive?.content
                        val title = jsonObject["title"]?.jsonPrimitive?.content

                        return@mapNotNull FileModel.Document(
                             uri = uri ,
                             type = type ,
                             format = format,
                             title = title,
                        )

                    }
                }
            } catch (e: Exception) {
                println("Error parsing attachment: ${e.message}")
                return@mapNotNull null
            }
            null
        }?.firstOrNull()

        return Message(
            userId = messageDTO.u._id,
            msg = messageDTO.msg,
            date = parseDateStringToLong(messageDTO.ts) ?: 0L,
            name = messageDTO.u.name ?: messageDTO.u.username,
            fileModel = fileModel
        )
    }

    private fun parseDateStringToLong(dateString: String): Long? {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Важно указать UTC
            val date: Date? = dateFormat.parse(dateString)
            date?.time // Возвращает Long (миллисекунды с эпохи)
        } catch (e: Exception) {
            // Обработка ошибок парсинга (например, неверный формат строки)
            println("Error parsing date: ${e.message}")
            null // Или выбросить исключение
        }
    }
}