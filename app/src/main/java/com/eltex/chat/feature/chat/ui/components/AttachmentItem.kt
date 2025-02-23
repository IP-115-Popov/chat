package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltex.chat.R
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.chat.viewmodel.LoadFileStatus
import com.eltex.chat.ui.components.MiddleEllipsisText
import com.eltex.chat.ui.components.ZoomableImage
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials
import com.eltex.chat.utils.openFile
import com.eltex.domain.models.FileModel
import kotlinx.coroutines.launch

@Composable
fun AttachmentItem(
    messageUiModel: MessageUiModel,
) {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    when (val file = messageUiModel.fileModel) {
        is FileModel.Document -> {
            val loadStatus = remember { mutableStateOf<LoadFileStatus>(LoadFileStatus.IsNotLoaded) }

            LaunchedEffect(file.uri) {
                if (chatViewModel.checkFileExists(file.uri) == true) {
                    loadStatus.value = LoadFileStatus.IsLoaded
                }
            }
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (loadStatus.value) {
                    LoadFileStatus.IsLoading -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    scope.launch {
                                        loadStatus.value = LoadFileStatus.IsLoading
                                        if (chatViewModel.loadDocument(file)) {
                                            loadStatus.value = LoadFileStatus.IsLoaded
                                        } else {
                                            loadStatus.value = LoadFileStatus.IsNotLoaded
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                color = CustomTheme.basicPalette.lightBlue,
                            )
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = CustomTheme.basicPalette.lightBlue
                            )
                        }

                    }

                    LoadFileStatus.IsNotLoaded -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = CustomTheme.basicPalette.lightBlue, shape = CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        loadStatus.value = LoadFileStatus.IsLoading
                                        if (chatViewModel.loadDocument(file)) {
                                            loadStatus.value = LoadFileStatus.IsLoaded
                                        } else {
                                            loadStatus.value = LoadFileStatus.IsNotLoaded
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_load),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = CustomTheme.basicPalette.white
                            )
                        }
                    }

                    LoadFileStatus.IsLoaded -> {

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = CustomTheme.basicPalette.lightBlue, shape = CircleShape
                                )
                                .clickable {
                                    context.openFile(messageUiModel.fileModel)
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_file),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = CustomTheme.basicPalette.white
                            )
                        }
                    }
                }
                Spacer(Modifier.size(4.dp))
                MiddleEllipsisText(
                    text = file.title ?: "Null",
                    style = CustomTheme.typographySfPro.bodyMedium,
                    color = CustomTheme.basicPalette.black,
                )
            }
        }

        is FileModel.Img -> {
            if (messageUiModel.bitmap != null) {
                val bitmap = messageUiModel.bitmap.asImageBitmap()
                val imageHeightPx = bitmap.height
                val imageWidthPx = bitmap.width
                val aspectRatio = remember(imageWidthPx, imageHeightPx) { imageWidthPx.toFloat() / imageHeightPx.toFloat() }
                var showPopup by remember { mutableStateOf(false) }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio)
                        .clickable {
                            showPopup = true
                        },
                    bitmap = bitmap,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                if (showPopup) {
                    ImgZoom(aspectRatio, bitmap, onBackClick = {showPopup = false} )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(CustomTheme.basicPalette.black),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        trackColor = CustomTheme.basicPalette.blue
                    )
                }
            }
        }

        is FileModel.Video -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .aspectRatio(1f)
                    .background(
                        color = Color.Black,
                    )
                    .clickable {
                        context.openFile(messageUiModel.fileModel)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = CustomTheme.basicPalette.white
                )
            }
        }

        null -> {}
    }
}

@Composable
private fun ImgZoom(
    aspectRatio: Float,
    bitmap: ImageBitmap,
    onBackClick: ()->Unit,
) {
    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(
            focusable = true
        ),
        onDismissRequest = { onBackClick() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(CustomTheme.basicPalette.black0)
        ) {
            Column(Modifier.background(CustomTheme.basicPalette.blue)) {
                Box(
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                )
                Row(
                    Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 26.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_24),
                            tint = CustomTheme.basicPalette.white,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onBackClick()
                                })
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = stringResource(R.string.back),
                            style = CustomTheme.typographySfPro.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = CustomTheme.basicPalette.white,
                        )
                    }
                }
                Box(
                    Modifier
                        .height(6.dp)
                        .fillMaxWidth()
                )
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ZoomableImage(
                    bitmap = bitmap, aspectRatio = aspectRatio
                )
            }
        }
    }
}