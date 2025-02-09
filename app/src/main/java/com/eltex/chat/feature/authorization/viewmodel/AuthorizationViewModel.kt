package com.eltex.chat.feature.authorization.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.authorization.repository.SignInRepository
import com.eltex.chat.feature.authorization.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val signInRepository: SignInRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorizationUiState())
    val state: StateFlow<AuthorizationUiState> = _state.asStateFlow()

    init {
        syncToken()
    }
    private fun syncToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenRepository.getToken()
            token?.let {
                tokenRepository.setToken(token)
            }
        }
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = signInRepository.signIn(state.value.user)
                if (result != "") {
                    tokenRepository.setToken(result)
                    tokenRepository.saveToken(result)
                }
            } catch (e: Exception) {
                Log.e("my-log", "Ошибка при регистрации: ${e.message}")
                e.printStackTrace()
            }
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