package com.eltex.chat.mvi

import kotlinx.coroutines.flow.Flow

interface EffectHandler<Effect, Message> {
    fun connect(effects: Flow<Effect>): Flow<Message>
}