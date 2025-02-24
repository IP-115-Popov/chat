package com.eltex.chat.feature.userprofile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.R
import com.eltex.chat.feature.userprofile.ui.components.WriteButton
import com.eltex.chat.feature.userprofile.viewmodel.UserProfileViewModel
import com.eltex.chat.ui.components.MainAvatar
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun UserProfileScreen(navController: NavHostController, userId: String) {
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
                        avatarImg =state.value.avatar,
                        name = state.value.user?.name
                    )
                    Box(Modifier.padding(6.dp).size(28.dp).background(color = background, shape = CircleShape).align(Alignment.BottomEnd)){
                        Box(Modifier.size(24.dp).background(color = CustomTheme.basicPalette.aquamarine, shape = CircleShape).align(Alignment.Center)){
                            Icon(
                                imageVector = Icons.Default.Check,
                                tint = CustomTheme.basicPalette.white,
                                contentDescription = null,
                                modifier =  Modifier.size(16.dp).align(Alignment.Center),
                            )
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

                WriteButton(onClick = {})
            }
        }
    }
}

@Preview
@Composable
fun UserProfileScreenPreview() {
    CustomTheme{
        UserProfileScreen(navController = rememberNavController(), userId = "")
    }
}
