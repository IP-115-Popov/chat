package com.eltex.chat.feature.userprofile.viewmodel

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.infochat.models.MemberUiModel
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.ChatModel
import com.eltex.domain.usecase.remote.GetAvatarUseCase
import com.eltex.domain.usecase.remote.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    fun getInfo(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfoUseCase(userId = userId).onRight { user ->
                _state.update {
                    it.copy(
                        user = user
                    )
                }
                getAvatar(user.username)
            }
        }
    }

    private fun getAvatar(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAvatarUseCase(
                subject = username
            ).onRight { avatarRes ->
                val img = avatarRes.byteArrayToBitmap()
                img?.let {
                    _state.update {
                        it.copy(
                            avatar = img
                        )
                    }
                }
            }
        }
    }
}