package com.eltex.chat.utils

fun String.getInitials(): String {
    return this.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() } // Получаем первую букву каждого слова, обрабатываем null
        .joinToString("") // Объединяем буквы в строку
}