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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltex.chat.R
import com.eltex.chat.feature.main.ui.components.ChatItem
import com.eltex.chat.feature.main.ui.components.SearchField
import com.eltex.chat.feature.main.viewmodel.MainUiStatus
import com.eltex.chat.feature.main.viewmodel.MainViewModel
import com.eltex.chat.feature.main.viewmodel.MessageStatus
import com.eltex.chat.ui.theme.CustomTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val state = mainViewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
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
                    .clickable { },
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
        val swipeRefreshState =
            rememberSwipeRefreshState(isRefreshing = isRefreshing)

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
                        //Text(text = it.name ?: "")

                        if (index == state.value.chatList.size - 1) {

                        }
                        when (index) {
                            0 -> {
                                ChatItem(
                                    imageText = chat.name,
                                    title = chat.name,
                                    message = chat.lastMessage,
                                    time = chat.lm,
                                    messageStatus = MessageStatus.missedMessages(chat.unread),
                                    bottomLine = false
                                )
                                HorizontalDivider()
                            }

                            state.value.chatList.size - 1 -> {
                                ChatItem(
                                    imageText = chat.name,
                                    title = chat.name,
                                    message = chat.lastMessage,
                                    time = chat.lm,
                                    messageStatus = MessageStatus.missedMessages(chat.unread),
                                    bottomLine = false
                                )
                            }

                            else -> {
                                ChatItem(
                                    imageText = chat.name,
                                    title = chat.name,
                                    message = chat.lastMessage,
                                    time = chat.lm,
                                    messageStatus = MessageStatus.missedMessages(chat.unread),
                                    bottomLine = true
                                )
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    CustomTheme {
        MainScreen()
    }
}