package com.eltex.chat.feature.chat.ui.formatters

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatDateHeader(dateString: String, locale: Locale = Locale("ru")): String {
    val today = Calendar.getInstance(locale)
    val yesterday = Calendar.getInstance(locale)
    yesterday.add(Calendar.DAY_OF_YEAR, -1)

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", locale)
    val headerDate = try {
        dateFormatter.parse(dateString)
    } catch (e: Exception) {
        println("Error parsing date string: $dateString")
        return "Неизвестная дата"
    }

    val calendar = Calendar.getInstance(locale)
    calendar.time = headerDate!!

    val todayString = dateFormatter.format(today.time)
    val yesterdayString = dateFormatter.format(yesterday.time)
    val headerDateString = dateFormatter.format(calendar.time)

    return when (headerDateString) {
        todayString -> "Сегодня"
        yesterdayString -> "Вчера"
        else -> {
            val yearFormat = SimpleDateFormat("dd MMMM yyyy", locale)
            val nonYearFormat = SimpleDateFormat("dd MMMM", locale)
            val currentYear = Calendar.getInstance(locale).get(Calendar.YEAR)
            val headerYear = calendar.get(Calendar.YEAR)
            if (currentYear == headerYear) {
                nonYearFormat.format(calendar.time)
            } else {
                yearFormat.format(calendar.time)
            }
        }
    }
}