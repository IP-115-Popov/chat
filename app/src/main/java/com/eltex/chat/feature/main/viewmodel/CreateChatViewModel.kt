package com.eltex.chat.feature.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.eltex.chat.feature.main.models.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<CreateChatUiState> = MutableStateFlow(CreateChatUiState())
    val state: StateFlow<CreateChatUiState> = _state.asStateFlow()

    fun setSearchValue(value: String) {
        _state.update {
            it.copy(
                searchValue = value
            )
        }
    }
    fun onContactSelected(userUiModel: UserUiModel) {
        Log.i("CreateChatViewModel", "create chat")
    }
}