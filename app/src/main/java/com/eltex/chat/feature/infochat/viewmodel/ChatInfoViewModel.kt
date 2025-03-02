package com.eltex.chat.feature.infochat.viewmodel

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.chat.feature.infochat.models.MemberUiModel
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.ChatModel
import com.eltex.domain.usecase.remote.GetAvatarUseCase
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
    private val getAvatarUseCase: GetAvatarUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<ChatInfoState> = MutableStateFlow(ChatInfoState())
    val state: StateFlow<ChatInfoState> = _state.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch(Dispatchers.IO) {
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
            if (state.value.profileModel == null) {
                getProfile().join()
            }

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
                loadMembersAvatar(membersList)
                loadChatAvatar(chat)
            }
        }
    }

    private suspend fun loadChatAvatar(chatModel: ChatModel) {
        getRoomAvatarUseCase(
            chat = chatModel, username = state.value.profileModel?.username
        )?.let { avatar ->
            _state.update {
                it.copy(
                    avatar = avatar.byteArrayToBitmap()
                )
            }
        }
    }

    private fun loadMembersAvatar(members: List<MemberUiModel>) {
        members.forEach { member ->
            if (member.avatar == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    getAvatarUseCase(
                        subject = member.username
                    ).onRight { avatarRes ->
                        val img = avatarRes.byteArrayToBitmap()
                        img?.let {
                            _state.update {
                                it.copy(membersList = it.membersList.map { m ->
                                    if (m == member) {
                                        m.copy(avatar = img.asImageBitmap())
                                    } else {
                                        m
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}