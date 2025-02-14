package com.eltex.chat.feature.chat.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.domain.usecase.GetMessageFromChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageFromChatUseCase: GetMessageFromChatUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageFromChatUseCase.execute(roomId =  "").collect{
                Log.i("ChatViewModel", it)
            }
        }
    }
}