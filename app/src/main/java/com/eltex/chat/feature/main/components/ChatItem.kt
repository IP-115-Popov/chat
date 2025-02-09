package com.eltex.chat.feature.main.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun ChatItem(
    imageText: String,
    title: String,
    message: String,
    time: String,
) {
    Row(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(CustomTheme.basicPalette.lightBlue, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = imageText,
                style = CustomTheme.typographySfPro.titleMedium,
                color = CustomTheme.basicPalette.white,
            )
        }
        Spacer(Modifier.size(8.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = CustomTheme.typographySfPro.bodyMedium500,
                    modifier = Modifier.weight(1f, fill = false),
                )
                Text(
                    text = time,
                    style = CustomTheme.typographySfPro.caption1Regular,
                    color = CustomTheme.basicPalette.grey,
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart, modifier = Modifier.height(20.dp)
            ) {
                Text(
                    text = message,
                    style = CustomTheme.typographySfPro.caption1Regular,
                    color = CustomTheme.basicPalette.grey,
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ", title = "Избранное", message = "Текст сообщения", time = "15:30"
        )
    }
}

@Preview
@Composable
fun ChatItemPreview2() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ",
            title = "ИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранное",
            message = "ТекстсообщенияТекстсообщенияТекстсообщениТекстсообщенияТекстсообщенияТекстсообщения",
            time = "15:30"
        )
    }
}