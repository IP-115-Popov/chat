package com.eltex.chat.feature.chat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.chat.ui.components.ChatScreenTopBar
import com.eltex.chat.feature.chat.ui.components.MessageItem
import com.eltex.chat.feature.chat.ui.components.MyMessageItem
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.formatters.InstantFormatter
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun ChatScreen(
    navController: NavController,
    roomId: String,
) {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state = chatViewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        chatViewModel.getChat(roomId = roomId)
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
    }) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(color = CustomTheme.basicPalette.white)) {
            items(state.value.messages) { message ->
                if (state.value.authData?.userId == message.userId) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        MessageItem(
                            text = message.msg,
                            title = message.name,
                            time = InstantFormatter.formatInstantToRelativeString(message.date)
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        MyMessageItem(
                            text = message.msg,
                            time = InstantFormatter.formatInstantToRelativeString(message.date),
                            read = true,
                        )
                    }
                }
                Spacer(Modifier.padding(11.dp))
            }
        }
    }
}
@Preview
@Composable
fun ChatScreenPreview() {
    CustomTheme {
        ChatScreen(navController = rememberNavController(), roomId = "")
    }
}