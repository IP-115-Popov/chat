package com.eltex.chat.feature.signin.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.R
import com.eltex.chat.feature.signin.mapper.LoginUiToLoginModelMapper
import com.eltex.domain.models.SignInError
import com.eltex.domain.usecase.SignInUseCase
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.websocket.ConnectWebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInUseCase: SignInUseCase,
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
    private val connectWebSocketUseCase: ConnectWebSocketUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SignInUiState())
    val state: StateFlow<SignInUiState> = _state.asStateFlow()

    init {
        syncToken()
    }

    private fun syncToken() {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    val authData = syncAuthDataUseCase.execute()
                    when (authData) {
                        is Either.Right -> {
                            connectWebSocket()
                            setStatus(SignInStatus.SignInSuccessful)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun connectWebSocket() {
        connectWebSocketUseCase.execute()
    }

    fun signIn() {
        setStatus(SignInStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginModel = LoginUiToLoginModelMapper.map(state.value.user)
                val authData = signInUseCase.execute(loginModel)

                when (authData) {
                    is Either.Left -> {
                        when (authData.value) {
                            SignInError.ConnectionMissing -> {
                                setStatus(SignInStatus.Error(context.getString(R.string.connection_is_missing)))
                            }

                            SignInError.Unauthorized -> {
                                setStatus(SignInStatus.Error(context.getString(R.string.Unauthorized)))
                            }
                        }
                    }

                    is Either.Right -> {
                        connectWebSocket()
                        setStatus(SignInStatus.SignInSuccessful)
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                Log.e("ViewModel", e.message ?: "")
                e.printStackTrace()
                setStatus(SignInStatus.Error(context.getString(R.string.connection_is_missing)))
            }
        }

    }

    fun setStatusIdle() {
        setStatus(SignInStatus.Idle)
    }

    private fun setStatus(signInStatus: SignInStatus) {
        _state.update {
            it.copy(
                status = signInStatus
            )
        }
    }

    fun setLogin(value: String) {
        _state.update {
            it.copy(
                user = it.user.copy(user = value)
            )
        }
    }

    fun setPassword(value: String) {
        _state.update {
            it.copy(
                user = it.user.copy(password = value)
            )
        }
    }
}