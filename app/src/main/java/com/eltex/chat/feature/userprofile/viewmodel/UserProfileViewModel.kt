package com.eltex.chat.feature.userprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.usecase.remote.CreateChatUseCase
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
    private val createChatUseCase: CreateChatUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    fun getInfo(userId: String) {
        setStatus(status = UserProfileStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfoUseCase(userId = userId).onRight { user ->
                _state.update {
                    it.copy(
                        user = user
                    )
                }
                getAvatar(user.username)
            }
            setStatus(status = UserProfileStatus.Idle)
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

    fun WriteClick() {
        setStatus(status = UserProfileStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            state.value.user?.username?.let { username ->
                val chatModel = createChatUseCase(
                    username = username
                )
                when (chatModel) {
                    is Either.Left -> {
                        setStatus(status = UserProfileStatus.Error(""))
                    }

                    is Either.Right -> {
                        _state.update {
                            it.copy(
                                status = UserProfileStatus.OpenChat(
                                    roomId = chatModel.value.id,
                                )
                            )
                        }
                        setStatus(status = UserProfileStatus.OpenChat(roomId = chatModel.value.id))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setStatus(status: UserProfileStatus) {
        _state.update {
            it.copy(
                status = status
            )
        }
    }
}