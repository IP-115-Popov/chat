package com.eltex.chat.feature.chat.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.chat.mappers.MessageToMessageUiModelMapper
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.models.MessagePayload
import com.eltex.domain.usecase.local.CheckFileExistsUseCase
import com.eltex.domain.usecase.remote.GetAvatarUseCase
import com.eltex.domain.usecase.remote.GetChatInfoUseCase
import com.eltex.domain.usecase.remote.GetHistoryChatUseCase
import com.eltex.domain.usecase.remote.GetImageUseCase
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetRoomAvatarUseCase
import com.eltex.domain.usecase.remote.GetUserInfoUseCase
import com.eltex.domain.usecase.remote.LoadDocumentUseCase
import com.eltex.domain.usecase.remote.SendMessageUseCase
import com.eltex.domain.Сonstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageFromChatUseCase: GetMessageFromChatUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getHistoryChatUseCase: GetHistoryChatUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val loadDocumentUseCase: LoadDocumentUseCase,
    private val checkFileExistsUseCase: CheckFileExistsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getRoomAvatarUseCase: GetRoomAvatarUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        syncAuthData()
    }

    private fun syncAuthData() {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    getProfileInfoUseCase().onRight { profileInfo ->
                        _state.update {
                            it.copy(
                                profileModel = profileInfo
                            )
                        }
                    }
                }
            }
        }
    }

    fun sync(roomId: String, roomType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val getChatInfoRes = getChatInfoUseCase(roomId = roomId)
            var chatName: String = ""
            getChatInfoRes.onRight { chat ->
                when (chat.t) {
                    "d" -> {
                        chat.uids?.firstOrNull { id -> id != state.value.profileModel?.id }?.let {
                            getUserInfoUseCase(it).onRight { user ->
                                chatName = user.name
                            }
                        }
                    }

                    else -> chatName = chat.name ?: ""

                }
                _state.update {
                    it.copy(
                        chatModel = chat
                    )
                }
                loadChatAvatar()
            }

            _state.update {
                it.copy(
                    roomId = roomId, roomType = roomType, name = chatName
                )
            }
            loadHistoryChat()
        }

        listenChat(roomId = roomId)
    }

    fun loadUserAvatar(username: String) {
        if (username !in state.value.usernameToAvatarsMap) {
            Log.i("loadUserAvatar", "${username}")
            viewModelScope.launch(Dispatchers.IO) {
                val userAvatarEither = getAvatarUseCase(subject = username)
                userAvatarEither.onRight { avatarBytes ->
                    val img = avatarBytes.byteArrayToBitmap()?.asImageBitmap()
                    if (img != null) {
                        _state.update { currentState ->
                            val updatedMap = (currentState.usernameToAvatarsMap?.toMutableMap()
                                ?: mutableMapOf()).apply {
                                this[username] = img
                            }.toMap()

                            currentState.copy(usernameToAvatarsMap = updatedMap)
                        }
                        Log.d("loadUsersAvatar", "Avatar loaded for $username")
                    } else {
                        Log.w("loadUsersAvatar", "Failed to decode avatar for $username")
                    }
                }.onLeft { error ->
                    Log.e("loadUsersAvatar", "Error loading avatar for $username: $error")
                }
            }
        }
    }

    private fun listenChat(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageFromChatUseCase(roomId = roomId).collect { messsage: Message ->
                withContext(Dispatchers.IO) {
                    _state.update { state ->
                        state.copy(
                            messages = (listOf(
                                MessageToMessageUiModelMapper.map(
                                    messsage
                                )
                            ) + state.messages).distinct()
                        )
                    }
                    messsage.username?.let { username ->
                        loadUserAvatar(username = username)
                    }
                    updateImg()
                }
            }
        }
    }


    fun setMsgText(value: String) {
        _state.update {
            it.copy(
                msgText = value
            )
        }
    }

    fun addAttachmentUri(uri: Uri) {
        _state.update {
            it.copy(
                attachmentUriList = it.attachmentUriList + uri
            )
        }
    }

    fun removeAttachmentUri(uri: Uri) {
        _state.update {
            it.copy(
                attachmentUriList = it.attachmentUriList - uri
            )
        }
    }

    fun clearAttachment() {
        _state.update {
            it.copy(
                attachmentUriList = emptySet()
            )
        }
    }

    fun sendDocument() {
        setStatus(ChatStatus.SendingMessage)
        viewModelScope.launch(Dispatchers.IO) {
            state.value.roomId?.let { roomId ->
                sendMessageUseCase(
                    MessagePayload(
                        roomId = roomId,
                        msg = "",
                        uri = state.value.attachmentUriList.first().toString()
                    )
                )
            }
            setStatus(ChatStatus.Idle)
            setMsgText("")
            clearAttachment()
        }
    }

    fun sendMessage() {
        if (state.value.msgText.isBlank() && state.value.attachmentUriList.isEmpty()) return
        val msgText = state.value.msgText
        setStatus(ChatStatus.SendingMessage)
        viewModelScope.launch(Dispatchers.IO) {
            when (state.value.attachmentUriList.size) {
                0 -> {
                    state.value.roomId?.let { roomId ->
                        sendMessageUseCase(
                            MessagePayload(
                                roomId = roomId,
                                msg = msgText,
                            )
                        )
                    }
                }

                else -> {
                    state.value.attachmentUriList.forEachIndexed { index, attachment ->
                        if (index == state.value.attachmentUriList.size - 1) {
                            state.value.roomId?.let { roomId ->
                                sendMessageUseCase(
                                    MessagePayload(
                                        roomId = roomId, msg = msgText, uri = attachment.toString()
                                    )
                                )
                            }
                        } else {
                            state.value.roomId?.let { roomId ->
                                sendMessageUseCase(
                                    MessagePayload(
                                        roomId = roomId, msg = "", uri = attachment.toString()
                                    )
                                )
                            }
                        }
                    }
                }
            }
            clearAttachment()
            setMsgText("")
            setStatus(ChatStatus.Idle)
        }
    }

    fun loadHistoryChat() {
        if (state.value.status == ChatStatus.NextPageLoading) return
        if (state.value.isAtEnd) return

        setStatus(ChatStatus.NextPageLoading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (state.value.roomId != null && state.value.roomType != null) {
                    val messages = getHistoryChatUseCase(
                        count = PAGE_SIZE,
                        offset = state.value.offset,
                        roomId = state.value.roomId!!,
                        roomType = state.value.roomType!!
                    ).onEach{ messsage ->
                        if (messsage.username != state.value.profileModel?.username) {
                            messsage.username?.let {
                                loadUserAvatar(username = it)
                            }
                        }
                    }.map {
                        MessageToMessageUiModelMapper.map(it)
                    }

                    withContext(Dispatchers.IO) {
                        if (messages.isEmpty()) {
                            _state.update { state ->
                                state.copy(
                                    isAtEnd = true
                                )
                            }
                        } else {
                            _state.update { state ->
                                Log.i(
                                    "ChatViewModel",
                                    messages.map { it.fileModel ?: "null" }.joinToString()
                                )
                                state.copy(
                                    offset = state.offset + messages.size,
                                    messages = (state.messages + messages).distinct()
                                )
                            }
                            updateImg()
                        }
                    }
                }
                setStatus(ChatStatus.Idle)
            } catch (e: Exception) {
                Log.e("ChatViewModel", "loadHistoryChat ${e.message}")
                setStatus(ChatStatus.Error)
            }
        }
    }

    private fun updateImg() {
        state.value.messages.forEach { message ->
            if (message.fileModel is FileModel.Img && message.bitmap == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    loadImg(fileModel = message.fileModel)?.let { bitmap ->
                        _state.update {
                            it.copy(messages = it.messages.map {
                                if (it == message) {
                                    it.copy(
                                        bitmap = bitmap
                                    )
                                } else {
                                    it
                                }
                            })
                        }
                    }
                }
            }
        }


        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                state.copy(
                    messages = state.messages.map { message ->
                        //если это незагруженное изображение
                        if (message.fileModel is FileModel.Img && message.bitmap == null) {
                            val bitmap = loadImg(fileModel = message.fileModel)
                            message.copy(bitmap = bitmap)
                        } else {
                            message
                        }
                    },
                )
            }
        }
    }

    private fun loadChatAvatar() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value.chatModel?.let { chat ->
                getRoomAvatarUseCase(
                    chat = chat, username = state.value.profileModel?.username
                )?.let { avatar ->
                    _state.update {
                        it.copy(
                            avatar = avatar.byteArrayToBitmap()?.asImageBitmap()
                        )
                    }
                }
            }
        }
    }

    suspend fun loadImg(fileModel: FileModel?) = when (val file = fileModel) {
        is FileModel.Img -> {
            try {
                val byteArray = getImageUseCase(Сonstants.BASE_URL + file.uri)
                when (byteArray) {
                    is Either.Left -> null
                    is Either.Right -> {
                        byteArray.value.byteArrayToBitmap()
                    }

                    else -> null
                }
            } catch (e: Exception) {
                Log.e(
                    "MessageToMessageUiModelMapper", "FileModel.Img ${e.message}"
                )
                null
            }
        }

        is FileModel.Document, is FileModel.Video, null -> null
    }

    suspend fun loadDocument(file: FileModel.Document): Boolean {
        val res: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            return@async try {
                if (loadDocumentUseCase(file.uri) != null) true
                else false
            } catch (e: Exception) {
                false
            }
        }
        return res.await()
    }

    suspend fun checkFileExists(uri: String): Boolean {
        val res = viewModelScope.async(Dispatchers.IO) {
            if (checkFileExistsUseCase(uri)) {
                return@async true
            } else {
                return@async false
            }
        }
        return res.await()
    }

    private fun setStatus(status: ChatStatus) {
        _state.update {
            it.copy(
                status = status
            )
        }
    }
}