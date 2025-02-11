package com.eltex.chat.feature.main.viewmodel

sealed interface MessageStatus {
    object yourLastMessageIsRead : MessageStatus
    object yourLastMessageIsNotRead : MessageStatus
    class missedMessages(val count: Int) : MessageStatus
}