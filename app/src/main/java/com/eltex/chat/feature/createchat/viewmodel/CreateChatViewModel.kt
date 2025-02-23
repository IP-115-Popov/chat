package com.eltex.chat.feature.createchat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.createchat.mappers.UserModelToUiModelMapper
import com.eltex.chat.feature.createchat.model.UserUiModel
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.usecase.remote.CreateChatUseCase
import com.eltex.domain.usecase.remote.GetAvatarUseCase
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import com.eltex.domain.websocket.WebSocketConnectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<CreateChatUiState> = MutableStateFlow(CreateChatUiState())
    val state: StateFlow<CreateChatUiState> = _state.asStateFlow()

    private val _connectionState =
        MutableStateFlow<WebSocketConnectionState>(WebSocketConnectionState.Disconnected)
    val connectionState: StateFlow<WebSocketConnectionState> = _connectionState.asStateFlow()

    init {
        searchUser()
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
                searchUser()
            }
        }
    }

    suspend fun onContactSelected(userUiModel: UserUiModel) {
        setStatus(status = CreateChatStatus.Loading)
        val chatModel = createChatUseCase(
            username = userUiModel.username
        )
        when (chatModel) {
            is Either.Left -> {
                setStatus(status = CreateChatStatus.Error(""))
            }

            is Either.Right -> {
                _state.update {
                    it.copy(
                        status = CreateChatStatus.roomCreated(
                            roomId = chatModel.value.id,
                            roomType = chatModel.value.t,
                        )
                    )
                }
            }

            else -> {}
        }
    }

    private fun searchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userlist = getUsersListUseCase(state.value.searchValue)
            when (userlist) {
                is Either.Left -> {
                    Log.i("CreateChatViewModel", "getUsersListUseCase left")
                }

                is Either.Right -> {

                    val updatedUserList = userlist.value.map { UserModelToUiModelMapper.map(it) }

                    withContext(Dispatchers.Main) {
                        _state.update {
                            it.copy(
                                userList = updatedUserList
                            )
                        }
                    }
                    loadUsersAvatar()
                }

                else -> {}
            }
        }
    }

    private fun loadUsersAvatar() {
        state.value.userList.forEach { user ->
            if (user.avatar == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    getAvatarUseCase(
                        subject = user.username
                    ).onRight { avatarRes ->
                        val img = avatarRes.byteArrayToBitmap()
                        img?.let {
                            _state.update {
                                it.copy(userList = it.userList.map { u ->
                                    if (u == user) {
                                        u.copy(avatar = img)
                                    } else {
                                        u
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val updatedUsers = state.value.userList.map { user ->
                if (user.avatar == null) {
                    val avatarRes = getAvatarUseCase(
                        subject = user.username
                    )
                    when (avatarRes) {
                        is Either.Left -> user
                        is Either.Right -> user.copy(avatar = avatarRes.value.byteArrayToBitmap())
                        else -> user
                    }
                } else {
                    user
                }
            }
            _state.update {
                it.copy(userList = updatedUsers)
            }
        }
    }

    fun setStatusIdle() {
        setStatus(status = CreateChatStatus.Idle)
    }

    private fun setStatus(status: CreateChatStatus) {
        _state.update {
            it.copy(
                status = status
            )
        }
    }
}