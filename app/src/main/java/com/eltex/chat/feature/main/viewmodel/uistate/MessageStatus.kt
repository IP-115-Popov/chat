package com.eltex.chat.feature.main.viewmodel.uistate

sealed interface MessageStatus {
    object yourLastMessageIsRead : MessageStatus
    object yourLastMessageIsNotRead : MessageStatus
    class missedMessages(val count: Int) : MessageStatus
}