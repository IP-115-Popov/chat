package com.eltex.chat.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.main.mappers.ChatToUIModelMapper
import com.eltex.chat.feature.main.mappers.ChatUIModelToChatModelMapper
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.profile.mappers.ProfileModelToProfileUiMapper
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.UserModel
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.usecase.remote.GetChatListUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetRoomAvatarUseCase
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import com.eltex.domain.usecase.remote.SubscribeToChatsUseCase
import com.eltex.domain.websocket.WebSocketConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
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
    private val getRoomAvatarUseCase: GetRoomAvatarUseCase,
    private val subscribeToChatsUseCase: SubscribeToChatsUseCase,
    private val getUsersListUseCase: GetUsersListUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    private val connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)

    init {
        connect()
    }

    private var chatsListCache: List<ChatUIModel> = emptyList()

    private fun connect() = viewModelScope.launch(Dispatchers.IO) {
        var profileJob: Job? = null
        var websocketJob: Job? = null
        if (state.value.profileUiModel == null) {
            profileJob = loadProfileInfo()
        }
        if (connectionState.value !is WebSocketConnectionState.Connected) {
            websocketJob = connectToWebSocket()
        }
        profileJob?.join()
        websocketJob?.join()
    }

    private fun loadProfileInfo() = viewModelScope.launch(Dispatchers.IO) {
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

    private fun connectToWebSocket() = viewModelScope.launch(Dispatchers.IO) {
        connectWebSocketUseCase().collect { state ->
            connectionState.value = state
            when (state) {
                is WebSocketConnectionState.Connected -> {
                    loadChat()
                    subscribeToChats()
                }

                is WebSocketConnectionState.Error -> {
                    setStatus(MainUiStatus.Error("Произошла ошибка, проверьте вашу сеть и повторите"))
                }

                is WebSocketConnectionState.Connecting -> {}
                is WebSocketConnectionState.Disconnected -> {}
            }
        }
    }

    private fun subscribeToChats() = viewModelScope.launch(Dispatchers.IO) {
        subscribeToChatsUseCase().collect { chatModel ->
            val usersList = searchUser().await()
            val chat = ChatToUIModelMapper.map(
                chatModel = chatModel, userId = state.value.profileUiModel?.id, usersList
            )

            _state.update {
                val updatedChatList = (listOf(chat) + it.chatList.filter { it.id != chat.id })
                chatsListCache = updatedChatList
                it.copy(chatList = updatedChatList)
            }
            loadAvatars()
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

    private fun get() = viewModelScope.launch(Dispatchers.IO) {
        connect().join()
        val usersList = searchUser().await()
        try {
            val res = getChatListUseCase()
            withContext(Dispatchers.Main) {
                _state.update {
                    val resfirst = res.first().map { chatModel ->
                        ChatToUIModelMapper.map(
                            chatModel = chatModel,
                            userId = state.value.profileUiModel?.id,
                            usersList
                        )
                    }.sortedByDescending { it.updatedAt }
                    chatsListCache = resfirst
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

    private fun searchUser() = viewModelScope.async(Dispatchers.IO) {
        getUsersListUseCase(state.value.searchValue).onRight { userlist ->
            return@async userlist
        }
        return@async emptyList<UserModel>()
    }

    private fun loadAvatars() {
        viewModelScope.launch {
            val avatarJobs = mutableListOf<Deferred<Unit?>>()
            state.value.chatList.filter { it.avatar == null }.forEach { chat ->
                val job = viewModelScope.async(Dispatchers.IO) {
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
                avatarJobs.add(job)
            }
            avatarJobs.awaitAll()
            chatsListCache = state.value.chatList
        }
    }

    fun setSearchValue(value: String) {
        _state.update {
            it.copy(
                searchValue = value
            )
        }
        if (value.length >= 2 || value.length == 0) {
            viewModelScope.launch {
                delay(300L)
                searchChat()
            }
        }
    }

    private fun searchChat() = viewModelScope.launch(Dispatchers.IO) {
        if (state.value.searchValue.length >= 2 || state.value.searchValue.length == 0) {
            _state.update { state ->
                state.copy(chatList = chatsListCache.filter {
                    it.name.contains(
                        state.searchValue, ignoreCase = true
                    )
                })
            }
        }
    }
}