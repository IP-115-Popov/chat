package com.eltex.chat.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.authorization.repository.AuthDataRepository
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
class ProfileViewModel @Inject constructor(
    private val authDataRepository: AuthDataRepository
): ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = authDataRepository.getAuthData()
            user?.let {
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            authData = user
                        )
                    }
                }
            }
        }
    }


}