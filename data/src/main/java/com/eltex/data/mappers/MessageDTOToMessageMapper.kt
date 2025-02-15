package com.eltex.data.mappers

import com.eltex.data.models.hitorymessge.MessageDTO
import com.eltex.domain.models.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object MessageDTOToMessageMapper {
    fun map(messageDTO: MessageDTO): Message = with(messageDTO) {
        Message(
            userId = u._id,
            msg = msg,
            date = parseDateStringToLong(ts) ?: 0L,
            name = u.username,
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