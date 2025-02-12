package com.eltex.chat.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.signin.viewmodel.SignInStatus
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.domain.usecase.GetChatListUseCase
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.websocket.WebSocketConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val connectWebSocketUseCase: ConnectWebSocketUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()

    init {
        connectToWebSocket()
    }

    private fun connectToWebSocket() {
        viewModelScope.launch {
            connectWebSocketUseCase.execute().collect { state ->
                _connectionState.value = state
                when (state) {
                    is WebSocketConnectionState.Connected -> {
                        get()
                    }

                    is WebSocketConnectionState.Disconnected -> {
                        _state.update {
                            it.copy(
                                status = MainUiStatus.Error("WebSocket Disconnected")
                            )
                        }
                    }

                    is WebSocketConnectionState.Connecting -> {
                        _state.update {
                            it.copy(
                                status = MainUiStatus.Loading
                            )
                        }
                    }

                    is WebSocketConnectionState.Error -> {
                        _state.update {
                            it.copy(
                                status = MainUiStatus.Error("WebSocket connection error")
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setStatus(status: MainUiStatus) {
        _state.update {
            it.copy(
                status = status
            )
        }
    }

    fun get() {
        setStatus(status = MainUiStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = getChatListUseCase.execute()
                withContext(Dispatchers.Main) {
                    _state.update {
                        val resfirst = res.first().map {
                            ChatUIModel(
                                id = it.id,
                                name = it.name,
                                lastMessage = it.lastMessage,
                                lm = it.lm?.let { instant ->
                                    InstantFormatter.formatInstantToRelativeString(
                                        instant
                                    )
                                } ?: "",
                                unread = it.unread, //Количество непрочитанных сообщений в комнате.
                                otrAck = "", //Статус подтверждения получения неофициального сообщения.
                                avatarUrl = "",
                            )
                        }
                        it.copy(chatList = resfirst)
                    }
                    setStatus(status = MainUiStatus.Idle)
                }
            } catch (e: Exception) {
                setStatus(status = MainUiStatus.Error("GetChat Error"))
                e.printStackTrace()
            }
        }
    }
}