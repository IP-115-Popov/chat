package com.eltex.chat.feature.chat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.chat.ui.components.CallerMessage
import com.eltex.chat.feature.chat.ui.components.ChatScreenTopBar
import com.eltex.chat.feature.chat.ui.components.MessageInput
import com.eltex.chat.feature.chat.ui.components.MessageItem
import com.eltex.chat.feature.chat.ui.components.MyMessageItem
import com.eltex.chat.feature.chat.viewmodel.ChatStatus
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.chat.ui.components.rememberLazyListStatePaginated
import com.eltex.chat.ui.theme.CustomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreen(
    navController: NavController,
    roomId: String,
    roomType: String,
) {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state = chatViewModel.state.collectAsState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { true })

    val showSendButtons by remember {
        derivedStateOf {
            state.value.msgText.isNotBlank() || state.value.attachmentUriList.isNotEmpty()
        }
    }
    val showAttachmentButtons by remember {
        derivedStateOf {
            state.value.msgText.isBlank() && !modalBottomSheetState.isVisible
        }
    }
    val enabled = rememberSaveable { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListStatePaginated {
        chatViewModel.loadHistoryChat()
    }

    LaunchedEffect(key1 = roomId, key2 = roomType) {
        chatViewModel.sync(roomId = roomId, roomType = roomType)
    }

    Scaffold(topBar = {
        ChatScreenTopBar(
            title = state.value.name ?: "",
            avatar = state.value.avatar,
            onBackClick = {
                navController.navigate(NavRoutes.Main.route) {
                    popUpTo(NavRoutes.Main.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            onMoreClick = {},
        )
    }, bottomBar = {
        MessageInput(
            value = state.value.msgText,
            showSendButtons = showSendButtons,
            showAttachmentButtons = showAttachmentButtons,
            enabled = enabled.value,
            onValueChange = { chatViewModel.setMsgText(it) },
            onAttachClick = {
                coroutineScope.launch {
                    modalBottomSheetState.show()
                }
            },
            onSendClick = {
                chatViewModel.sendMessage()
                if (modalBottomSheetState.isVisible) {
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }
            },
        )
    }) { innerPadding ->
        MediaPickerBottomSheet(
            modalBottomSheetState = modalBottomSheetState,
        ) {
            LazyColumn(
                state = listState,
                userScrollEnabled = enabled.value,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .background(color = CustomTheme.basicPalette.white),
                reverseLayout = true
            ) {
                items(items = state.value.messages) { message ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val maxMessageWidth = LocalConfiguration.current.screenWidthDp.dp * 0.6f
                        if (state.value.roomType == "d") {
                            if (state.value.profileModel?.id == message.userId) {
                                MyMessageItem(
                                    text = message.msg,
                                    time = InstantFormatter.formatInstantToRelativeString(message.date),
                                    read = true,
                                    messageUiModel = message,
                                    modifier = Modifier
                                        .widthIn(max = maxMessageWidth)
                                        .align(Alignment.CenterEnd)
                                )
                            } else {
                                CallerMessage(
                                    text = message.msg,
                                    time = InstantFormatter.formatInstantToRelativeString(message.date),
                                    messageUiModel = message,
                                    modifier = Modifier
                                        .widthIn(max = maxMessageWidth)
                                        .align(Alignment.CenterStart)
                                )
                            }
                        } else {
                            if (state.value.profileModel?.id == message.userId) {
                                MyMessageItem(
                                    text = message.msg,
                                    time = InstantFormatter.formatInstantToRelativeString(message.date),
                                    read = true,
                                    messageUiModel = message,
                                    modifier = Modifier
                                        .widthIn(max = maxMessageWidth)
                                        .align(Alignment.CenterEnd)
                                )
                            } else {
                                MessageItem(
                                    text = message.msg,
                                    title = message.username,
                                    time = InstantFormatter.formatInstantToRelativeString(message.date),
                                    messageUiModel = message,
                                    messageModifier = Modifier
                                        .widthIn(max = maxMessageWidth)
                                        .align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.padding(11.dp))
                }
                if (state.value.status is ChatStatus.NextPageLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .height(118.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {

                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    when (state.value.status) {
        ChatStatus.Error, ChatStatus.Loading, ChatStatus.NextPageLoading, ChatStatus.Idle -> {
            enabled.value = true
            LaunchedEffect(modalBottomSheetState.currentValue) {
                if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
                    chatViewModel.clearAttachment()
                }
            }
        }

        ChatStatus.SendingMessage -> {
            enabled.value = false
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = CustomTheme.basicPalette.blue
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    CustomTheme {
        ChatScreen(navController = rememberNavController(), roomId = "", roomType = "")
    }
}