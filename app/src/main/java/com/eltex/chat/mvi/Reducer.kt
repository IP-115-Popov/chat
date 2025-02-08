package com.eltex.chat.mvi

interface Reducer<State, Effect, Message> {
    fun reduce(old: State, message: Message): ReducerResult<State, Effect>
}