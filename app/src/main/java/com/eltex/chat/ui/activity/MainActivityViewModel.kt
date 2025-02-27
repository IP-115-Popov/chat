package com.eltex.chat.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private var syncAuthDataUseCase: SyncAuthDataUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainActivityState())
    val state: StateFlow<MainActivityState> = _state.asStateFlow()

    fun syncAuthData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                syncAuthDataUseCase().onRight {
                    _state.update {
                        it.copy(
                            isUserRegistered = true
                        )
                    }
                }
            }
            delay(1000L)
            _state.update {
                it.copy(
                    isLoadingEnd = true
                )
            }
        }
    }
}