package com.eltex.chat.feature.authorization.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.R
import com.eltex.domain.feature.autorization.usecase.SyncAuthDataUseCase
import com.eltex.domain.feature.autorization.usecase.SignInUseCase
import com.eltex.domain.models.LoginModel
import com.eltex.domain.models.SignInError
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
class AuthorizationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInUseCase: SignInUseCase,
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationUiState())
    val state: StateFlow<AuthorizationUiState> = _state.asStateFlow()

    init {
        syncToken()
    }

    private fun syncToken() {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                val authData = syncAuthDataUseCase.execute()
                when (authData) {
                    is Either.Right -> {
                        setStatus(AuthorizationStatus.AuthorizationSuccessful)
                    }
                    else -> {}
                }
            }
        }
    }

    fun signIn() {
        setStatus(AuthorizationStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authData = signInUseCase.execute(LoginModel(
                    user = state.value.user.user,
                    password =  state.value.user.password
                ))
                when (authData) {
                    is Either.Left -> {
                        when (authData.value) {
                            SignInError.ConnectionMissing -> {
                                setStatus(AuthorizationStatus.Error(context.getString(R.string.connection_is_missing)))
                            }

                            SignInError.Unauthorized -> {
                                setStatus(AuthorizationStatus.Error(context.getString(R.string.Unauthorized)))
                            }
                        }
                    }
                    is Either.Right -> {
                        setStatus(AuthorizationStatus.AuthorizationSuccessful)
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                setStatus(AuthorizationStatus.Error(context.getString(R.string.connection_is_missing)))
            }
        }

    }

    fun setStatusIdle() {
        setStatus(AuthorizationStatus.Idle)
    }

    private fun setStatus(authorizationStatus: AuthorizationStatus) {
        _state.update {
            it.copy(
                status = authorizationStatus
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