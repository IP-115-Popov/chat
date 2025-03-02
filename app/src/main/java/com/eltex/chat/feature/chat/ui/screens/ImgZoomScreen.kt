package com.eltex.chat.feature.chat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.eltex.chat.R
import com.eltex.chat.ui.components.ZoomableImage
import com.eltex.chat.ui.theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImgZoomScreen(
    aspectRatio: Float,
    text: String,
    bitmap: ImageBitmap,
    onBackClick: () -> Unit,
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Popup(alignment = Alignment.Center, properties = PopupProperties(
        focusable = true
    ), onDismissRequest = { onBackClick() }) {
        Scaffold(
            Modifier
                .fillMaxSize()
                .background(CustomTheme.basicPalette.black0),
            topBar = {
                Column(Modifier.background(CustomTheme.basicPalette.blue)) {
                    Box(
                        Modifier
                            .height(48.dp - systemBarsPadding.calculateTopPadding())
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
            }, bottomBar = {
                Box(
                    Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(if (text.isBlank()) CustomTheme.basicPalette.blue else CustomTheme.basicPalette.black0)
                )
            }) { innerPadding ->
            if (text.isNotBlank()) {
                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
                val bottomSheetHeight = remember { mutableStateOf(30.dp) }
                val shadowColor = CustomTheme.basicPalette.black0

                BottomSheetScaffold(modifier = Modifier.padding(innerPadding),
                    scaffoldState = bottomSheetScaffoldState,
                    backgroundColor = CustomTheme.basicPalette.black0,
                    sheetBackgroundColor = CustomTheme.basicPalette.black0.copy(alpha = 0.8f),
                    sheetPeekHeight = bottomSheetHeight.value,
                    sheetContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 31.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    text = text,
                                    color = CustomTheme.basicPalette.white,
                                    style = CustomTheme.typographySfPro.bodyRegular,
                                    textAlign = TextAlign.Start
                                )
                            }
                            if (bottomSheetScaffoldState.bottomSheetState.currentValue == BottomSheetValue.Collapsed) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .align(Alignment.BottomCenter)
                                        .drawBehind {
                                            drawRect(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(Color.Transparent, shadowColor),
                                                    startY = 0f,
                                                    endY = size.height
                                                )
                                            )
                                        }
                                )
                            }
                        }
                    }) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        ZoomableImage(
                            bitmap = bitmap, aspectRatio = aspectRatio
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CustomTheme.basicPalette.black0),
                    contentAlignment = Alignment.Center
                ) {
                    ZoomableImage(
                        bitmap = bitmap, aspectRatio = aspectRatio
                    )
                }
            }
        }
    }
}