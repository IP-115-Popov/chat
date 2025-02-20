package com.eltex.chat.utils

fun String.getInitials(): String {
    return this.split(" ")
        .mapNotNull {
            it.firstOrNull()?.toString()
        }
        .take(2)
        .joinToString("")
        .toUpperCase()
}