package com.eltex.chat.utils

fun String.getInitials(): String {
    return this.split(" ")
        .mapNotNull {
            it.firstOrNull()?.toString()
        }
        .joinToString("")
        .toUpperCase()
}