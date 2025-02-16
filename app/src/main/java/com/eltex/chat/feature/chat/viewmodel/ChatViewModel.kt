package com.eltex.chat.feature.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.chat.mappers.MessageToMessageUiModelMapper
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.GetHistoryChatUseCase
import com.eltex.domain.usecase.GetMessageFromChatUseCase
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageFromChatUseCase: GetMessageFromChatUseCase,
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
    private val getHistoryChatUseCase: GetHistoryChatUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        syncAuthData()
    }

    private fun syncAuthData() {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    syncAuthDataUseCase.execute().onRight { authData ->
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

    fun sync(roomId: String, roomType: String) {
        _state.update {
            it.copy(
                roomId = roomId,
                roomType = roomType,
            )
        }
    }

    fun listenChat() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value.roomId?.let { roomId ->
                getMessageFromChatUseCase.execute(roomId = roomId)
                    .collect { messsage: Message ->
                        withContext(Dispatchers.IO) {
                            _state.update { state ->
                                state.copy(
                                    messages = listOf(
                                        MessageToMessageUiModelMapper.map(
                                            messsage
                                        )
                                    ) + state.messages
                                )
                            }
                        }
                    }
            }
        }
    }

    fun loadHistoryChat() {
        if (state.value.status == ChatStatus.NextPageLoading) return
        if (state.value.isAtEnd) return

        setStatus(ChatStatus.NextPageLoading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (state.value.roomId != null && state.value.roomType != null) {
                    val message = getHistoryChatUseCase.execute(
                        count = PAGE_SIZE,
                        offset = state.value.offset,
                        roomId = state.value.roomId!!,
                        roomType = state.value.roomType!!
                    ).map {
                        MessageToMessageUiModelMapper.map(it)
                    }

                    withContext(Dispatchers.IO) {
                        if (message.isEmpty()) {
                            _state.update { state ->
                                state.copy(
                                    isAtEnd = true
                                )
                            }
                        } else {
                            _state.update { state ->
                                Log.i("ChatViewModel", message.map { it.fileModel ?: "null" }.joinToString() )
                                state.copy(
                                    offset = state.offset + message.size,
                                    messages = state.messages + message
                                )
                            }
                        }
                        setStatus(ChatStatus.Idle)
                    }
                } else {
                    setStatus(ChatStatus.Error)
                }
            } catch (e: Exception) {
                setStatus(ChatStatus.Error)
            }
        }
    }

    private fun setStatus(status: ChatStatus) {
        _state.update {
            it.copy(
                status = status
            )
        }
    }
}