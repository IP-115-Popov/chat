package com.eltex.chat.feature.chat.ui.components

import androidx.compose.runtime.Composable
import com.eltex.chat.feature.chat.model.MessageUiModel

@Composable
fun MessageItem(
    title: String,
    text: String,
    time: String,
    messageUiModel: MessageUiModel,
) {
    if (text.length > 33 || messageUiModel != null) {
        BigMessageItem(
            title = title,
            text = text,
            time = time,
            messageUiModel = messageUiModel,
        )
    } else {
        LittleMessageItem(
            title = title,
            text = text,
            time = time,
        )
    }
}