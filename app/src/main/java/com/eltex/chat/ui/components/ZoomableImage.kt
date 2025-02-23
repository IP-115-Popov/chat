package com.eltex.chat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
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
            .clip(RectangleShape)
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
