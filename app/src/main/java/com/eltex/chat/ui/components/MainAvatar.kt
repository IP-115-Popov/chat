package com.eltex.chat.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials

@Composable
fun MainAvatar(avatarImg: Bitmap?, name: String?) {
    val avatarPainter = remember(avatarImg) {
        avatarImg?.asImageBitmap()?.let { BitmapPainter(it) }
    }

    if (avatarPainter != null) {
        Image(
            painter = avatarPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(118.dp)
                .clip(CircleShape),
        )
    } else {
        val initials = remember(name) { name?.getInitials() ?: "ФИО" }
        Box(
            modifier = Modifier
                .size(118.dp)
                .background(CustomTheme.basicPalette.grey2, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = CustomTheme.typographySfPro.titleLarge,
                color = CustomTheme.basicPalette.darkGray,
                maxLines = 1,
            )
        }
    }
}