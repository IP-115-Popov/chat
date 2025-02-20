package com.eltex.chat.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.chat.mappers.MessageToMessageUiModelMapper
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.profile.mappers.ProfileModelToProfileUiMapper
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.usecase.remote.GetChatListUseCase
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetUserInfoUseCase
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
    private val connectWebSocketUseCase: ConnectWebSocketUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getMessageFromChatUseCase: GetMessageFromChatUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()

    init {
        connectToWebSocket()
        loadProfileInfo()
    }

    private fun loadProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val profileModel = getProfileInfoUseCase()
            profileModel.onRight { getProfileInfoResult ->
                _state.update {
                    it.copy(
                        profileUiModel = ProfileModelToProfileUiMapper.map(getProfileInfoResult)
                    )
                }
            }
        }
    }

    private fun connectToWebSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            connectWebSocketUseCase().collect { state ->
                _connectionState.value = state
                when (state) {
                    is WebSocketConnectionState.Connected -> {
                        loadChat()
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

    fun loadChat() {
        setStatus(status = MainUiStatus.Loading)
        get()
    }

    fun refreshChat() {
        setStatus(status = MainUiStatus.IsRefreshing)
        get()
    }

    private fun get() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = getChatListUseCase()
                withContext(Dispatchers.Main) {
                    _state.update {
                        val resfirst = res.first().map { chatModel ->
                            val name: String = if (chatModel.t == "d") {
                                chatModel.uids?.firstOrNull {id -> id != state.value.profileUiModel?.id}?.let {userId ->
                                    when (val user = getUserInfoUseCase.invoke(userId)) {
                                        is Either.Left -> chatModel.name ?: ""
                                        is Either.Right -> user.value.name
                                        else -> ""
                                    }
                                } ?: ""
                            } else {
                                chatModel.name ?: ""
                            }

                            val lastMessage = chatModel.message?.let { message ->
                                getLastMessage(
                                    messsage = message, chatType = chatModel.t
                                )
                            } ?: "Сообщений нет"

                            ChatUIModel(
                                id = chatModel.id,
                                name = name,
                                lastMessage = lastMessage,
                                lm = chatModel.lm?.let { instant ->
                                    InstantFormatter.formatInstantToRelativeString(
                                        instant
                                    )
                                } ?: "",
                                unread = chatModel.unread ?: 0,
                                otrAck = "",
                                avatarUrl = "",
                                usernames = chatModel.usernames,
                                t = chatModel.t,
                            )
                        }.onEach { chat ->
                            listenChat(roomId = chat.id)
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

    private fun getLastMessage(messsage: Message, chatType: String): String {
        var lastMessage = when (messsage.fileModel) {
            is FileModel.Document -> "Документ"
            is FileModel.Img -> "Изображение"
            is FileModel.Video -> "Видео"
            null -> if (messsage.msg.length > 0) messsage.msg else return "Сообщений нет"
        }

        if (chatType != "d") {
            val fio = when (messsage.userId) {
                state.value.profileUiModel?.id -> "Вы"
                else -> messsage.name
            }
            lastMessage = fio + ":  " + lastMessage
        }
        return lastMessage
    }

    fun listenChat(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageFromChatUseCase(roomId = roomId).collect { messsage: Message ->
                withContext(Dispatchers.IO) {
                    _state.update { state ->
                        state.copy(chatList = state.chatList.map { chat ->
                            if (chat.id == messsage.rid) {
                                val lastMessage =
                                    getLastMessage(messsage = messsage, chatType = chat.t)

                                chat.copy(
                                    lastMessage = lastMessage
                                )
                            } else {
                                chat
                            }
                        })
                    }
                }
            }
        }
    }
}