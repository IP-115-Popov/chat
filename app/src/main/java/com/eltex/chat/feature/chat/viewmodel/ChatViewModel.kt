package com.eltex.chat.feature.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.chat.mappers.MessageToMessageUiModelMapper
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.GetMessageFromChatUseCase
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageFromChatUseCase: GetMessageFromChatUseCase,
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    init {
        syncAuthData()
    }

    private fun syncAuthData() {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    syncAuthDataUseCase.execute().onRight{ authData ->
                        _state.update {
                            it.copy(
                                authData = authData
                            )
                        }
                    }
                }
            }
        }
    }

    fun getChat(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageFromChatUseCase.execute(roomId = roomId).collect{ messsage: Message ->
                _state.update {
                    it.copy(
                        messages = it.messages + MessageToMessageUiModelMapper.map(messsage)
                    )
                }
            }
        }
    }
}