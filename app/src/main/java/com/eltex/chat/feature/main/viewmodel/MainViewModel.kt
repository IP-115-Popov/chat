package com.eltex.chat.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.main.mappers.ChatUIModelToChatModelMapper
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.profile.mappers.ProfileModelToProfileUiMapper
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.usecase.remote.GetChatListUseCase
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetRoomAvatarUseCase
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
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getRoomAvatarUseCase: GetRoomAvatarUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            connect()
        }
    }

    private suspend fun connect() {
        if (state.value.profileUiModel == null) {
            loadProfileInfo()
        }
        if (connectionState.value !is WebSocketConnectionState.Connected) {
            connectToWebSocket()
        }
    }

    private suspend fun loadProfileInfo() {
        val profileModel = getProfileInfoUseCase()
        profileModel.onRight { getProfileInfoResult ->
            _state.update {
                it.copy(
                    profileUiModel = ProfileModelToProfileUiMapper.map(getProfileInfoResult)
                )
            }
        }.onLeft {
            setStatus(MainUiStatus.Error("Произошла ошибка, проверьте вашу сеть и повторите"))
        }
    }

    private suspend fun connectToWebSocket() {
        connectWebSocketUseCase().collect { state ->
            _connectionState.value = state
            when (state) {
                is WebSocketConnectionState.Connected -> {
                    loadChat()
                }

                is WebSocketConnectionState.Connecting -> {

                }

                is WebSocketConnectionState.Disconnected, is WebSocketConnectionState.Error -> {
                    setStatus(MainUiStatus.Error("Произошла ошибка, проверьте вашу сеть и повторите"))
                }
            }
        }
    }

    fun setStatusIdle() = setStatus(status = MainUiStatus.Idle)

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
            connect()
            try {
                val res = getChatListUseCase()
                withContext(Dispatchers.Main) {
                    _state.update {
                        val resfirst = res.first().map { chatModel ->
                            val name: String = if (chatModel.t == "d") {
                                chatModel.uids?.firstOrNull { id -> id != state.value.profileUiModel?.id }
                                    ?.let { userId ->
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
                            if (chat !in state.value.chatList) {
                                listenChat(roomId = chat.id)
                            }
                        }
                        it.copy(chatList = resfirst)
                    }
                    loadAvatars()
                    setStatus(status = MainUiStatus.Idle)
                }
            } catch (e: Exception) {
                setStatus(status = MainUiStatus.Error("GetChat Error"))
                e.printStackTrace()
            }
        }
    }

    private fun loadAvatars() {
        state.value.chatList.forEach { chat ->
            if (chat.avatar == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    val chatModel = ChatUIModelToChatModelMapper.map(chat)
                    getRoomAvatarUseCase(
                        chat = chatModel, username = state.value.profileUiModel?.username
                    )?.let { avatar ->
                        val img = avatar.byteArrayToBitmap()
                        img?.let {
                            _state.update {
                                it.copy(chatList = it.chatList.map { c ->
                                    if (c == chat) {
                                        c.copy(avatar = img)
                                    } else {
                                        c
                                    }
                                })
                            }
                        }
                    }
                }
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