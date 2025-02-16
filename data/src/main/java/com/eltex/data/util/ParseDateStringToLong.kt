package com.eltex.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun parseDateStringToLong(dateString: String): Long? {
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