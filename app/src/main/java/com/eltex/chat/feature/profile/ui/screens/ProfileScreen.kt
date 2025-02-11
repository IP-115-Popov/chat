package com.eltex.chat.feature.profile.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.eltex.chat.R
import com.eltex.chat.feature.profile.ui.components.ExitAlertDialog
import com.eltex.chat.feature.profile.viewmodel.ProfileStatus
import com.eltex.chat.feature.profile.viewmodel.ProfileViewModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials


@Composable
fun ProfileScreen() {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val state = profileViewModel.state.collectAsState()
    val showExitAlertDialog = remember { mutableStateOf(false) }

    if (showExitAlertDialog.value) {
        ExitAlertDialog(onDismissRequest = { showExitAlertDialog.value = false },
            onExitRequest = { profileViewModel.exitFromProfile() })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.basicPalette.white1)
    ) {
        Box(
            modifier = Modifier
                .height(142.dp)
                .fillMaxWidth()
                .background(CustomTheme.basicPalette.blue),
        )

        Box(
            modifier = Modifier
                .size(124.dp)
                .offset(y = -62.dp)
                .background(color = CustomTheme.basicPalette.white1, shape = CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
        ) {
            var imageLoadError by remember { mutableStateOf(false) }

            if ((!state.value.profileUiModel?.avatarUrl.isNullOrEmpty()) && !imageLoadError) {
                AsyncImage(model = state.value.profileUiModel?.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(118.dp)
                        .clip(CircleShape),

                    onError = {
                        imageLoadError = true
                    })
            } else {
                Box(
                    modifier = Modifier
                        .size(118.dp)
                        .background(CustomTheme.basicPalette.grey2, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.value.profileUiModel?.name?.getInitials() ?: "ФИО",
                        style = CustomTheme.typographyRoboto.titleLarge,
                        color = CustomTheme.basicPalette.darkGray,
                        maxLines = 1,
                    )
                }
            }
        }
        Text(
            text = state.value.profileUiModel?.name ?: "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (8 - 59).dp),
            style = CustomTheme.typographyRoboto.titleMedium
        )

        Spacer(modifier = Modifier.height(190.dp))

        LogoutButton {
            showExitAlertDialog.value = true
        }
    }

    when (state.value.status) {
        ProfileStatus.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileStatus.Error -> {}
        ProfileStatus.Idle -> {}
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(48.dp)
        .background(
            CustomTheme.basicPalette.white,
            RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
        )
        .clickable { onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 11.dp)
                .padding(start = 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = CustomTheme.basicPalette.lightBlue
                )
                Spacer(modifier = Modifier.width(11.dp))
                Text(
                    text = "Выйти", style = CustomTheme.typographySfPro.headlineSemibold
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                contentDescription = null,
                modifier = Modifier
                    .height(13.33.dp)
                    .padding(end = 1.dp),
                tint = CustomTheme.basicPalette.lightGrey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CustomTheme {
        ProfileScreen()
    }
}