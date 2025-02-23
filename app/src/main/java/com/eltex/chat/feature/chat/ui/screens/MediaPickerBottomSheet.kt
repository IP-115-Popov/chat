package com.eltex.chat.feature.chat.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltex.chat.R
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.ui.theme.CustomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaPickerBottomSheet(
    modifier: Modifier = Modifier,
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state = chatViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val media = listOf(Media.Image, Media.Video, Media.Document)
    val selectedOption = remember { mutableStateOf(media[0]) }

    ModalBottomSheetLayout(modifier = modifier,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp), contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        Modifier
                            .size(width = 36.dp, height = 5.dp)
                            .background(
                                CustomTheme.basicPalette.lightGrey,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                }
                Spacer(Modifier.size(8.dp))

                Row(
                    modifier = Modifier
                        .height(96.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    media.forEach { mediaItem ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = if (selectedOption.value == mediaItem) CustomTheme.basicPalette.lightBlue
                                        else CustomTheme.basicPalette.lightGrayBlue,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedOption.value = mediaItem
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(mediaItem.vectorId),
                                    tint = CustomTheme.basicPalette.white,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(Modifier.size(4.dp))
                            Text(
                                text = mediaItem.title,
                                maxLines = 1,
                                color = CustomTheme.basicPalette.darkGray,
                                style = CustomTheme.typographySfPro.caption1Regular,
                            )
                        }
                    }
                }
            }
            Box(
                Modifier
                    .heightIn(min = 300.dp)
                    .fillMaxWidth()
            ) {
                when (selectedOption.value) {
                    Media.Image -> {
                        chatViewModel.clearAttachment()
                        MediaGrid()
                    }

                    Media.Document -> {
                        chatViewModel.clearAttachment()
                        val documentPickerLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.OpenDocument(),
                            onResult = { uri ->
                                uri?.let {
                                    chatViewModel.addAttachmentUri(uri)
                                    coroutineScope.launch {
                                        modalBottomSheetState.hide()
                                    }
                                    chatViewModel.sendDocument()
                                }
                            })

                        InternalStorageButton(onClick = {
                            documentPickerLauncher.launch(arrayOf("*/*"))
                        })
                    }

                    Media.Video -> {
                        chatViewModel.clearAttachment()
                    }
                }
            }

        }) {
        content()
    }
}

sealed class Media(val title: String, val vectorId: Int) {
    object Image : Media(title = "Изображение", vectorId = R.drawable.ic_photo)
    object Video : Media(title = "Видео", vectorId = R.drawable.ic_videocam)
    object Document : Media(title = "Документ", vectorId = R.drawable.ic_draft)
}
