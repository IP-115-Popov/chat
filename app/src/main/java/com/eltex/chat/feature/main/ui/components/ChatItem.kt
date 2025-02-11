package com.eltex.chat.feature.main.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.feature.main.viewmodel.MessageStatus
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun ChatItem(
    imageText: String,
    title: String,
    message: String,
    time: String,
    messageStatus: MessageStatus
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (messageStatus) {
                        MessageStatus.yourLastMessageIsNotRead -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_done),
                                contentDescription = null,
                                tint = CustomTheme.basicPalette.lightBlue
                            )
                        }

                        MessageStatus.yourLastMessageIsRead -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_done_all),
                                contentDescription = null,
                                tint = CustomTheme.basicPalette.lightBlue
                            )
                        }

                        else -> {}
                    }
                    Spacer(Modifier.size(2.dp))
                    Text(
                        text = time,
                        style = CustomTheme.typographySfPro.caption1Regular,
                        color = CustomTheme.basicPalette.grey,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = message,
                    style = CustomTheme.typographySfPro.caption1Regular,
                    color = CustomTheme.basicPalette.grey,
                    modifier = Modifier.weight(1f, fill = false),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(41.dp))
                if (messageStatus is MessageStatus.missedMessages && messageStatus.count > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(CustomTheme.basicPalette.lightBlue, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (messageStatus.count < 10) messageStatus.count.toString() else "...",
                            style = CustomTheme.typographySfPro.caption2Regular,
                            color = CustomTheme.basicPalette.white,
                        )
                    }
                } else {
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ", title = "Избранное", message = "Текст сообщения", time = "15:30",
            messageStatus = MessageStatus.missedMessages(9)
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
            time = "15:30",
            messageStatus = MessageStatus.missedMessages(0)
        )
    }
}

@Preview
@Composable
fun ChatItemPreview3() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ",
            title = "ИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранное",
            message = "ТекстсообщенияТекстсообщенияТекстсообщениТекстсообщенияТекстсообщенияТекстсообщения",
            time = "15:30",
            messageStatus = MessageStatus.yourLastMessageIsRead
        )
    }
}

@Preview
@Composable
fun ChatItemPreview4() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ",
            title = "ИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранное",
            message = "ТекстсообщенияТекстсообщенияТекстсообщениТекстсообщенияТекстсообщенияТекстсообщения",
            time = "Вчера",
            messageStatus = MessageStatus.yourLastMessageIsNotRead
        )
    }
}

@Preview
@Composable
fun ChatItemPreview5() {
    CustomTheme {
        ChatItem(
            imageText = "ФИ",
            title = "ИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранноеИзбранное",
            message = "ТекстсообщенияТекстсообщенияТекстсообщениТекстсообщенияТекстсообщенияТекстсообщения",
            time = "Пн",
            messageStatus = MessageStatus.yourLastMessageIsNotRead
        )
    }
}