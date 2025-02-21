package com.eltex.chat.feature.main.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.R
import com.eltex.chat.feature.createchat.ui.components.SearchField
import com.eltex.chat.feature.createchat.ui.screens.BottomCreatedChatScreen
import com.eltex.chat.feature.main.models.ChatUIModel
import com.eltex.chat.feature.main.ui.components.ChatItem
import com.eltex.chat.feature.main.viewmodel.MainUiStatus
import com.eltex.chat.feature.main.viewmodel.MainViewModel
import com.eltex.chat.feature.main.viewmodel.MessageStatus
import com.eltex.chat.feature.navigationBar.BottomNavigationBar
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.ui.theme.CustomTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val state = mainViewModel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { true })

    Scaffold(bottomBar = {
        BottomNavigationBar(navController)
    }) { innerPadding ->
        BottomCreatedChatScreen(
            navController = navController,
            modalBottomSheetState = modalBottomSheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(CustomTheme.basicPalette.blue)
                )
                Box(
                    Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .background(CustomTheme.basicPalette.blue)
                ) {
                    Text(
                        text = stringResource(R.string.chats),
                        style = CustomTheme.typographySfPro.titleMedium,
                        modifier = Modifier.align(Alignment.Center),
                        color = CustomTheme.basicPalette.white
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .clickable {
                                coroutineScope.launch {
                                    if (modalBottomSheetState.isVisible) {
                                        modalBottomSheetState.hide()
                                    } else {
                                        modalBottomSheetState.show()
                                    }
                                }
                            },
                        tint = CustomTheme.basicPalette.white
                    )

                }
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .background(CustomTheme.basicPalette.blue)
                        .padding(horizontal = 16.dp)
                ) {
                    SearchField(
                        value = "",
                        placeholderText = stringResource(R.string.chat_search_placeholder),
                        onValueChange = {},
                        onClearClick = {},
                    )
                }

                val isRefreshing by remember { derivedStateOf { (state.value.status is MainUiStatus.IsRefreshing) } }
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

                SwipeRefresh(state = swipeRefreshState,
                    onRefresh = { mainViewModel.refreshChat() },
                    indicator = { state, refrashTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refrashTrigger,
                            contentColor = CustomTheme.basicPalette.blue
                        )
                    }) {
                    Column {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(state.value.chatList) { index, chat ->
                                when (index) {
                                    0 -> {
                                        ChatItem(imageText = chat.name,
                                            title = chat.name,
                                            avatar = chat.avatar,
                                            message = chat.lastMessage,
                                            time = chat.lm,
                                            messageStatus = MessageStatus.missedMessages(0),
                                            bottomLine = false,
                                            onClick = {
                                                navToChat(navController, chat)
                                            })
                                        HorizontalDivider()
                                    }

                                    state.value.chatList.size - 1 -> {
                                        ChatItem(imageText = chat.name,
                                            title = chat.name,
                                            avatar = chat.avatar,
                                            message = chat.lastMessage,
                                            time = chat.lm,
                                            messageStatus = MessageStatus.missedMessages(0),
                                            bottomLine = false,
                                            onClick = { navToChat(navController, chat) })
                                    }

                                    else -> {
                                        ChatItem(imageText = chat.name,
                                            title = chat.name,
                                            avatar = chat.avatar,
                                            message = chat.lastMessage,
                                            time = chat.lm,
                                            messageStatus = MessageStatus.missedMessages(0),
                                            bottomLine = true,
                                            onClick = { navToChat(navController, chat) })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        when (state.value.status) {
            MainUiStatus.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = CustomTheme.basicPalette.blue
                    )
                }

            }

            is MainUiStatus.Error, MainUiStatus.Idle, MainUiStatus.IsRefreshing -> {
            }
        }
    }
}

private fun navToChat(
    navController: NavController, chat: ChatUIModel
) {
    navController.navigate(NavRoutes.Chat.route + "/${chat.id}" + "/${chat.t}") {
        popUpTo(NavRoutes.Chat.route) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
    }
}