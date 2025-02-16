package com.eltex.data.mappers

import com.eltex.domain.models.FileModel
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object AttachmentsToFileModelMapper {
    fun map(attachment: JsonElement): FileModel? {
        val jsonElement: JsonElement = attachment
        val jsonObject = jsonElement.jsonObject
        if (jsonObject.containsKey("type") && jsonObject.containsKey("title_link")) {
            val type = jsonObject["type"]?.jsonPrimitive?.content
            val uri = jsonObject["title_link"]?.jsonPrimitive?.content

            if (type == null || uri == null) return null

            jsonObject["image_type"]?.jsonPrimitive?.content?.let { image_type ->
                return FileModel.Img(
                    uri = uri,
                    format = image_type,
                )
            }

            jsonObject["video_type"]?.jsonPrimitive?.content?.let { video_type ->
                return FileModel.Video(
                    uri = uri,
                    format = video_type,
                )
            }

            jsonObject["video_type"]?.jsonPrimitive?.content?.let { video_type ->
                return FileModel.Video(
                    uri = uri,
                    format = video_type,
                )
            }




            if (jsonObject.containsKey("format")) {
                val format = jsonObject["format"]?.jsonPrimitive?.content
                val title = jsonObject["title"]?.jsonPrimitive?.content

                return FileModel.Document(
                    uri = uri,
                    type = type,
                    format = format,
                    title = title,
                )
            } else return null
        } else return null
    }
}