package com.eltex.chat.feature.chat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.chat.ui.components.ChatScreenTopBar
import com.eltex.chat.feature.chat.ui.components.MessageInput
import com.eltex.chat.feature.chat.ui.components.MessageItem
import com.eltex.chat.feature.chat.ui.components.MyMessageItem
import com.eltex.chat.feature.chat.viewmodel.ChatStatus
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.createchat.ui.screens.BottomCreatedChatScreen
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
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { true })
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListStatePaginated {
        chatViewModel.loadHistoryChat()
    }

    LaunchedEffect(key1 = roomId, key2 = roomType) {
        chatViewModel.sync(roomId = roomId, roomType = roomType)
        chatViewModel.loadHistoryChat()
        chatViewModel.listenChat()
    }

    Scaffold(topBar = {
        ChatScreenTopBar(
            onBackClick = {
                navController.navigate(NavRoutes.Main.route) {
                    popUpTo(NavRoutes.Main.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            title = "roomId",
            onMoreClick = {},
        )
    }, bottomBar = {
        MessageInput(
            value = state.value.searchText,
            onValueChange = { chatViewModel.setSearchText(it) },
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
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .background(color = CustomTheme.basicPalette.white),
                reverseLayout = true
            ) {
                items(state.value.messages) { message ->
                    if (state.value.authData?.userId == message.userId) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            MyMessageItem(
                                text = message.msg,
                                time = InstantFormatter.formatInstantToRelativeString(message.date),
                                read = true,
                                messageUiModel = message,
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            MessageItem(
                                text = message.msg,
                                title = message.username,
                                time = InstantFormatter.formatInstantToRelativeString(message.date),
                                messageUiModel = message,
                            )
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
}

@Preview
@Composable
fun ChatScreenPreview() {
    CustomTheme {
        ChatScreen(navController = rememberNavController(), roomId = "", roomType = "")
    }
}