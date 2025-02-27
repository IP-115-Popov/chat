package com.eltex.chat.feature.chat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
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
import com.eltex.chat.feature.chat.ui.components.PrivateChatScreenTopBar
import com.eltex.chat.feature.chat.ui.formatters.formatDateHeader
import com.eltex.chat.feature.chat.viewmodel.ChatStatus
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.chat.ui.theme.CustomTheme
import kotlinx.coroutines.launch
import java.util.Calendar


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

    val listState = rememberLazyListState()

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val totalItemsCount = listState.layoutInfo.totalItemsCount

        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let { lastVisibleIndex ->
            if (totalItemsCount > 0 && lastVisibleIndex > (totalItemsCount - 2)) {
                chatViewModel.loadHistoryChat()
            }
        }
    }

    LaunchedEffect(listState.layoutInfo.totalItemsCount) {
        val totalItemsCount = listState.layoutInfo.totalItemsCount

        listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index?.let { firstVisibleIndex ->
            if (totalItemsCount > 0 && firstVisibleIndex < 1) {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }



    LaunchedEffect(key1 = roomId, key2 = roomType) {
        chatViewModel.sync(roomId = roomId, roomType = roomType)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        val onBackClick = {
            navController.navigate(NavRoutes.Main.route) {
                popUpTo(NavRoutes.Main.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
        if (state.value.chatModel?.t == "d") {
            PrivateChatScreenTopBar(
                title = state.value.name ?: "",
                avatar = state.value.avatar,
                onBackClick = onBackClick,
                onMoreClick = {
                    state.value.recipientUserId?.let { userId ->
                        navController.navigate(NavRoutes.UserProfile.route + "/${userId}") {
                            launchSingleTop = true
                        }
                    }
                },
            )
        } else {
            val usersCount = state.value.chatModel?.usersCount ?: 0
            ChatScreenTopBar(
                title = state.value.name ?: "",
                usersCount = usersCount,
                avatar = state.value.avatar,
                onBackClick = onBackClick,
                onMoreClick = {
                    navController.navigate(NavRoutes.ChatInfo.route + "/${roomId}" + "/${roomType}") {
                        launchSingleTop = true
                    }
                },
            )
        }
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
            val maxMessageWidth = LocalConfiguration.current.screenWidthDp.dp * 0.6f

            val groupedMessages = state.value.messages.groupBy { message ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = message.date
                calendar.get(Calendar.YEAR).toString() + "-" + calendar.get(Calendar.MONTH)
                    .toString() + "-" + calendar.get(Calendar.DAY_OF_MONTH).toString()
            }

            Text("")
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(top = 16.dp),
                userScrollEnabled = enabled.value,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .background(color = CustomTheme.basicPalette.white),
                reverseLayout = true
            ) {

                groupedMessages.forEach { (dateString, messagesForDate) ->
                    items(items = messagesForDate) { message ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            val isMyMessage = state.value.profileModel?.id == message.userId
                            val alignment =
                                if (isMyMessage) Alignment.CenterEnd else Alignment.CenterStart

                            if (state.value.roomType == "d") {
                                if (isMyMessage) {
                                    MyMessageItem(
                                        text = message.msg,
                                        time = InstantFormatter.formatInstantToRelativeString(
                                            message.date
                                        ),
                                        read = true,
                                        messageUiModel = message,
                                        modifier = Modifier
                                            .widthIn(max = maxMessageWidth)
                                            .align(alignment)
                                    )
                                } else {
                                    CallerMessage(
                                        text = message.msg,
                                        time = InstantFormatter.formatInstantToRelativeString(
                                            message.date
                                        ),
                                        messageUiModel = message,
                                        modifier = Modifier
                                            .widthIn(max = maxMessageWidth)
                                            .align(alignment)
                                            .clip(shape = RoundedCornerShape(15.dp))
                                    )
                                }
                            } else {
                                if (isMyMessage) {
                                    MyMessageItem(
                                        text = message.msg,
                                        time = InstantFormatter.formatInstantToRelativeString(
                                            message.date
                                        ),
                                        read = true,
                                        messageUiModel = message,
                                        modifier = Modifier
                                            .widthIn(max = maxMessageWidth)
                                            .align(alignment)
                                    )
                                } else {
                                    MessageItem(
                                        avatar = state.value.usernameToAvatarsMap.getOrDefault(
                                            key = message.username, defaultValue = null
                                        ),
                                        text = message.msg,
                                        title = message.name,
                                        time = InstantFormatter.formatInstantToRelativeString(
                                            message.date
                                        ),
                                        messageUiModel = message,
                                        messageModifier = Modifier
                                            .widthIn(max = maxMessageWidth)
                                            .align(alignment)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.padding(11.dp))
                    }
                    item {
                        Text(
                            text = formatDateHeader(dateString),
                            style = CustomTheme.typographySfPro.caption1Medium,
                            color = CustomTheme.basicPalette.grey,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.padding(12.dp))
                    }
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