package com.eltex.chat.feature.infochat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.infochat.models.MemberUiModel
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.ChatModel
import com.eltex.domain.usecase.remote.GetChatInfoUseCase
import com.eltex.domain.usecase.remote.GetChatMembersUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetRoomAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatInfoViewModel @Inject constructor(
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val getRoomAvatarUseCase: GetRoomAvatarUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getChatMembersUseCase: GetChatMembersUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<ChatInfoState> = MutableStateFlow(ChatInfoState())
    val state: StateFlow<ChatInfoState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getProfile()
            loadChatAvatar()
        }
    }

    private suspend fun getProfile() {
        getProfileInfoUseCase().onRight { profileModel ->
            _state.update {
                it.copy(
                    profileModel = profileModel
                )
            }
        }
    }

    fun getInfo(roomId: String, roomType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var chatModel: ChatModel? = null
            var membersList: List<MemberUiModel> = emptyList()
            getChatInfoUseCase(roomId = roomId).onRight { chat ->
                chatModel = chat
                getChatMembersUseCase(chat).onRight { members ->
                    membersList = members.map {
                        MemberUiModel(
                            id = it.id,
                            name = it.name,
                            avatar = null,
                            status = it.status,
                            username = it.username,
                        )
                    }
                }
                _state.update {
                    it.copy(
                        roomId = roomId,
                        roomType = roomType,
                        chatModel = chatModel,
                        membersList = membersList
                    )
                }
            }
        }
    }

    private suspend fun loadChatAvatar() {
        state.value.chatModel?.let { chat ->
            getRoomAvatarUseCase(
                chat = chat, username = state.value.profileModel?.username
            )?.let { avatar ->
                _state.update {
                    it.copy(
                        avatar = avatar.byteArrayToBitmap()
                    )
                }
            }
        }
    }
}