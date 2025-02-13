package com.eltex.chat.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.main.mappers.UserModelToUiModelMapper
import com.eltex.chat.feature.main.models.UserUiModel
import com.eltex.domain.usecase.CreateChatUseCase
import com.eltex.domain.usecase.GetUsersListUseCase
import com.eltex.domain.websocket.WebSocketConnectionState
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
class CreateChatViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
    private val createChatUseCase: CreateChatUseCase,
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
        searchUser()
    }
    fun onContactSelected(userUiModel: UserUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            createChatUseCase.execute(
                userUiModel.username,
            ).collect{
                Log.i("CreateChatViewModel", "create chat")
            }
        }
        Log.i("CreateChatViewModel", "create chat")
    }

    private fun searchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userlist = getUsersListUseCase.execute(state.value.searchValue, count = 20, offset = 0)
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
                }
                else -> {}
            }
        }
    }
}