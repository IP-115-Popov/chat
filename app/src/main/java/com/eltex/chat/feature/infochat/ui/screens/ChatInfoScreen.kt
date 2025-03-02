package com.eltex.chat.feature.infochat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eltex.chat.R
import com.eltex.chat.feature.infochat.ui.components.MemberItem
import com.eltex.chat.feature.infochat.viewmodel.ChatInfoViewModel
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.ui.components.MainAvatar
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun ChatInfoScreen(
    navController: NavHostController,
    roomId: String,
    roomType: String,
    chatInfoVIewModel: ChatInfoViewModel
) {
    val state = chatInfoVIewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        chatInfoVIewModel.getInfo(roomId = roomId, roomType = roomType)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(CustomTheme.basicPalette.white1)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(142.dp)
                    .fillMaxWidth()
                    .background(CustomTheme.basicPalette.blue),
            ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                    contentDescription = null,
                    tint = CustomTheme.basicPalette.white,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp)
                        .size(24.dp)
                        .align(Alignment.BottomStart)
                        .clickable {
                            navController.navigateUp()
                        })
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 82.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(122.dp)
                        .background(CustomTheme.basicPalette.white1, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    MainAvatar(
                        avatarImg = state.value.avatar, name = state.value.chatModel?.name
                    )
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    text = state.value.chatModel?.name ?: "",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = CustomTheme.typographySfPro.titleMedium
                )
                Text(
                    text = stringResource(R.string.participants) + state.value.membersList.size.toString(),
                    color = CustomTheme.basicPalette.grey,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = CustomTheme.typographySfPro.caption1Regular
                )
                Spacer(Modifier.size(17.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .background(
                            color = CustomTheme.basicPalette.white,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    itemsIndexed(
                        state.value.membersList,
                        key = { i, item -> item.id }) { i, member ->
                        val drawHorizontalDivider = (i != state.value.membersList.lastIndex)
                        MemberItem(
                            member = member,
                            onSelect = {
                                if (member.id == state.value.profileModel?.id) {
                                    navController.navigate(NavRoutes.Profile.route) {
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(NavRoutes.UserProfile.route + "/${member.id}") {
                                        launchSingleTop = true
                                    }
                                }
                            },
                            drawHorizontalDivider = drawHorizontalDivider
                        )
                    }
                }
            }
        }
    }
}