package com.eltex.chat.feature.chat.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.feature.chat.mappers.MessageToMessageUiModelMapper
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.models.FileModel
import com.eltex.domain.models.Message
import com.eltex.domain.models.MessagePayload
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.usecase.local.CheckFileExistsUseCase
import com.eltex.domain.usecase.remote.GetHistoryChatUseCase
import com.eltex.domain.usecase.remote.GetImageUseCase
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import com.eltex.domain.usecase.remote.LoadDocumentUseCase
import com.eltex.domain.usecase.remote.SendMessageUseCase
import com.eltex.domain.Сonstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
    private val getHistoryChatUseCase: GetHistoryChatUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val loadDocumentUseCase: LoadDocumentUseCase,
    private val checkFileExistsUseCase: CheckFileExistsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
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
                    syncAuthDataUseCase().onRight { authData ->
                        _state.update {
                            it.copy(
                                authData = authData
                            )
                        }
                    }
                }
            }
        }
    }

    fun sync(roomId: String, roomType: String) {
        _state.update {
            it.copy(
                roomId = roomId,
                roomType = roomType,
            )
        }
    }

    fun listenChat() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value.roomId?.let { roomId ->
                getMessageFromChatUseCase(roomId = roomId).collect { messsage: Message ->
                    withContext(Dispatchers.IO) {
                        _state.update { state ->
                            state.copy(
                                messages = listOf(
                                    MessageToMessageUiModelMapper.map(
                                        messsage
                                    )
                                ) + state.messages
                            )
                        }
                    }
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
        Log.i("chat vm", state.value.attachmentUriList.toString())
    }
    fun removeAttachmentUri(uri: Uri) {
        _state.update {
            it.copy(
                attachmentUriList = it.attachmentUriList - uri
            )
        }
        Log.i("chat vm", state.value.attachmentUriList.toString())
    }

    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            sendMessageUseCase(
                MessagePayload(
                    id = "fgfpkFDHGDH",
                    roomId = state.value.roomId ?: "67b164ebcc5c71ade77b82cc",
                    msg = state.value.msgText,
                    token = "a8G7X5sFhDKWL8XlweMOfHq5NWY2igJklc-KaPFxSJQ"
                )
            )
            setMsgText("")
        }
    }

    fun loadHistoryChat() {
        if (state.value.status == ChatStatus.NextPageLoading) return
        if (state.value.isAtEnd) return

        setStatus(ChatStatus.NextPageLoading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (state.value.roomId != null && state.value.roomType != null) {
                    val message = getHistoryChatUseCase(
                        count = PAGE_SIZE,
                        offset = state.value.offset,
                        roomId = state.value.roomId!!,
                        roomType = state.value.roomType!!
                    ).map {
                        MessageToMessageUiModelMapper.map(it)
                    }

                    withContext(Dispatchers.IO) {
                        if (message.isEmpty()) {
                            _state.update { state ->
                                state.copy(
                                    isAtEnd = true
                                )
                            }
                        } else {
                            _state.update { state ->
                                Log.i(
                                    "ChatViewModel",
                                    message.map { it.fileModel ?: "null" }.joinToString()
                                )
                                state.copy(
                                    offset = state.offset + message.size,
                                    messages = state.messages + message
                                )
                            }
                            updateImg()
                        }
                        setStatus(ChatStatus.Idle)
                    }
                } else {
                    setStatus(ChatStatus.Error)
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "loadHistoryChat ${e.message}")
                setStatus(ChatStatus.Error)
            }
        }
    }

    private suspend fun updateImg() {
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

    suspend fun loadImg(fileModel: FileModel?) = when (val file = fileModel) {
        is FileModel.Img -> {
            try {
                val byteArray =
                    getImageUseCase(Сonstants.BASE_URL + file.uri)
                when (byteArray) {
                    is Either.Left -> null
                    is Either.Right -> {
                        byteArray.value.byteArrayToBitmap()
                    }

                    else -> null
                }
            } catch (e: Exception) {
                Log.e(
                    "MessageToMessageUiModelMapper",
                    "FileModel.Img ${e.message}"
                )
                null
            }
        }

        is FileModel.Document,
        is FileModel.Video, null -> null
    }

    suspend fun loadDocument(file: FileModel.Document): Boolean {
        val res: Deferred<Boolean> = viewModelScope.async(Dispatchers.IO) {
            return@async try {
                if (loadDocumentUseCase(file.uri) != null)
                    true
                else
                    false
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