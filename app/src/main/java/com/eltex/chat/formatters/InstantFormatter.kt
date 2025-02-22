package com.eltex.chat.formatters

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object InstantFormatter {
    private val zoneId: ZoneId = ZoneId.systemDefault() // Или ZoneId.of("Europe/Moscow")
    private val locale: Locale = Locale("ru", "RU")
    private val dayOfWeekFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EE", locale)
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM", locale)
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)

    fun formatInstantToRelativeString(timestamp: Long): String { // Принимаем Long timestamp
        val instant = Instant.ofEpochMilli(timestamp) // Преобразуем Long в Instant
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)
        val today = LocalDate.now(zoneId)
        val yesterday = today.minusDays(1)
        val date = zonedDateTime.toLocalDate()

        return when {
            date == today -> zonedDateTime.format(timeFormatter)
            date == yesterday -> "вчера"
            date.isAfter(today.minusWeeks(1)) -> {
                val dayOfWeek = zonedDateTime.format(dayOfWeekFormatter)
                val firstLetter = dayOfWeek.substring(0, 1).uppercase(locale)
                val secondLetter = dayOfWeek.substring(1, 2).lowercase(locale)
                firstLetter + secondLetter
            }

            else -> zonedDateTime.format(dateFormatter)
        }
    }
}