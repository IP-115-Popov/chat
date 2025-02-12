package com.eltex.chat.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.domain.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        get()
    }
     fun get() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getChatListUseCase.execute()
            getChatListUseCase.execute().onEach {
                _state.update {
                    val resfirst =  res.first().map { it
                        ChatUIModel (
                            id  = it.id,
                            name = it.name,
                            lastMessage = it.lastMessage,
                            lm = it.lm?.let {instant ->   InstantFormatter.formatInstantToRelativeString(instant)} ?: "",
                            unread = it.unread, //Количество непрочитанных сообщений в комнате.
                            otrAck = "", //Статус подтверждения получения неофициального сообщения.
                            avatarUrl = "",
                        )
                    }
                    it.copy(chatList = resfirst)
                }
            }.launchIn(viewModelScope)
        }
    }
}