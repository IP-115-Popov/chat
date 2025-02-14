package com.eltex.chat.feature.chat.ui.components

import androidx.compose.runtime.Composable

@Composable
fun MessageItem(
    title: String,
    text: String,
    time: String,
) {
    if (text.length <= 33) {
        LittleMessageItem(
            title = title,
            text = text,
            time = time,
        )
    } else {
        BigMessageItem(
            title = title,
            text = text,
            time = time,
        )
    }
}