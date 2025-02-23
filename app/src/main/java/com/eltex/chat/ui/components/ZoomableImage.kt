package com.eltex.chat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale

@Composable
fun ZoomableImage(
    bitmap: androidx.compose.ui.graphics.ImageBitmap, aspectRatio: Float
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val minScale = 1f
    val maxScale = 5f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    val newScale = (scale * zoom).coerceIn(minScale, maxScale)
                    val newOffset = offset + pan

                    scale = newScale
                    offset = newOffset
                }
            }, contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(aspectRatio)
                .graphicsLayer(
                    scaleX = scale, scaleY = scale, translationX = offset.x, translationY = offset.y
                ), bitmap = bitmap, contentDescription = null, contentScale = ContentScale.Fit
        )
    }
}
