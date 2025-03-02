package com.eltex.chat.feature.userprofile.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eltex.chat.R
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.feature.userprofile.ui.components.UserStatusAway
import com.eltex.chat.feature.userprofile.ui.components.UserStatusBusy
import com.eltex.chat.feature.userprofile.ui.components.UserStatusOffline
import com.eltex.chat.feature.userprofile.ui.components.UserStatusOnline
import com.eltex.chat.feature.userprofile.ui.components.WriteButton
import com.eltex.chat.feature.userprofile.viewmodel.UserProfileStatus
import com.eltex.chat.feature.userprofile.viewmodel.UserProfileViewModel
import com.eltex.chat.ui.components.MainAvatar
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun UserProfileScreen(
    navController: NavHostController,
    userId: String,
) {
    val userProfileViewModel = hiltViewModel<UserProfileViewModel>()
    val state = userProfileViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        userProfileViewModel.getInfo(userId = userId)
    }
    val background = CustomTheme.basicPalette.white1
    Box(
        Modifier
            .fillMaxSize()
            .background(background)
    ) {
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
                        avatarImg = state.value.avatar, name = state.value.user?.name
                    )
                    Box(
                        Modifier
                            .padding(6.dp)
                            .size(28.dp)
                            .background(color = background, shape = CircleShape)
                            .align(Alignment.BottomEnd)
                    ) {
                        val statusModifier: Modifier = Modifier.align(Alignment.Center)

                        when (state.value.user?.status) {
                            "online" -> {
                                UserStatusOnline(modifier = statusModifier)
                            }

                            "offline" -> {
                                UserStatusOffline(modifier = statusModifier)
                            }

                            "away" -> {
                                UserStatusAway(modifier = statusModifier)
                            }

                            "busy", "invisible" -> {
                                UserStatusBusy(modifier = statusModifier)
                            }

                            else -> {}
                        }

                    }
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    text = state.value.user?.name ?: "",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = CustomTheme.typographySfPro.titleMedium
                )
                Spacer(Modifier.size(8.dp))

                WriteButton(onClick = {
                    userProfileViewModel.WriteClick()
                })
            }
        }
    }
    when (val status = state.value.status) {
        UserProfileStatus.Idle -> {}
        is UserProfileStatus.Error -> {}
        UserProfileStatus.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = CustomTheme.basicPalette.blue
                )
            }
        }

        is UserProfileStatus.OpenChat -> {
            LaunchedEffect(Unit) {
                navController.navigate(NavRoutes.Chat.route + "/${status.roomId}" + "/d") {
                    popUpTo(NavRoutes.Chat.route) {
                        inclusive = true
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }
}