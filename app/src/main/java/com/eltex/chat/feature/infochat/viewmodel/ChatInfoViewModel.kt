package com.eltex.chat.feature.infochat.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatInfoViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
): ViewModel() {
    private val _state: MutableStateFlow<ChatInfoState> = MutableStateFlow(ChatInfoState())
    val state: StateFlow<ChatInfoState> = _state.asStateFlow()
}