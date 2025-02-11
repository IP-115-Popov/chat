package com.eltex.chat.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getChatListUseCase: GetChatListUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("getChatListUseCase", "execute")
            val res = getChatListUseCase.execute()
            getChatListUseCase.execute().onEach {
                _state.update {
                    val resfirst =  res.first()
                    Log.i("MainViewModel", resfirst.toString())
                    it.copy(chatList = it.chatList + resfirst)
                }
            }.launchIn(viewModelScope)
        }
    }
}