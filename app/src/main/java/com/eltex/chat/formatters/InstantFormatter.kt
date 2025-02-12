package com.eltex.chat.formatters

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object InstantFormatter {
    fun formatInstantToRelativeString(instant: Instant, locale: Locale = Locale.getDefault()): String {
        val now = Instant.now()
        val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val date = zonedDateTime.toLocalDate()

        return when {
            date == today -> {
                val formatter = DateTimeFormatter.ofPattern("HH:mm", locale)
                zonedDateTime.format(formatter)
            }
            date == yesterday -> "вчера"
            date.isAfter(today.minusWeeks(1)) -> {
                val formatter = DateTimeFormatter.ofPattern("EE", locale) // EE - короткое название дня недели
                zonedDateTime.format(formatter)
            }
            else -> {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", locale)
                zonedDateTime.format(formatter)
            }
        }
    }
}