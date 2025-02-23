package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials

@Composable
fun MessageItem(
    avatar: ImageBitmap?,
    title: String,
    text: String,
    time: String,
    messageModifier: Modifier,
    messageUiModel: MessageUiModel,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
    ) {
        if (avatar != null) {
            Image(
                bitmap = avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(shape = CircleShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(CustomTheme.basicPalette.lightBlue, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.getInitials(),
                    style = CustomTheme.typographySfPro.titleMedium,
                    color = CustomTheme.basicPalette.white,
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        if (text.length > 33 || messageUiModel.fileModel != null) {
            BigMessageItem(
                title = title,
                text = text,
                time = time,
                messageUiModel = messageUiModel,
                modifier = messageModifier,
            )
        } else {
            LittleMessageItem(
                title = title,
                text = text,
                time = time,
                modifier = messageModifier,
            )
        }
    }
}