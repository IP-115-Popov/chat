package com.eltex.chat.feature.chat.ui.components.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MessagesSelectingChatScreenTopBar(
    messageCount: Int,
    onCancelClick: () -> Unit,
) {
    Column(Modifier.background(CustomTheme.basicPalette.blue)) {
        Box(
            Modifier
                .height(48.dp)
                .fillMaxWidth()
        )
        Box(
            Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            ) {
            Text(
                text = stringResource(R.string.selected) + ": " + messageCount,
                style = CustomTheme.typographySfPro.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = CustomTheme.basicPalette.white,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = stringResource(R.string.cancel2),
                style = CustomTheme.typographySfPro.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = CustomTheme.basicPalette.white,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onCancelClick()
                    }
            )
        }
    }
}

@Preview
@Composable
fun MessagesSelectingChatScreenTopBarPreview() {
    CustomTheme {
        MessagesSelectingChatScreenTopBar(
            messageCount = 1,
            onCancelClick = {},
        )
    }
}

@Preview
@Composable
fun MessagesSelectingChatScreenTopBarPreview2() {
    CustomTheme {
        MessagesSelectingChatScreenTopBar(
            messageCount = 100,
            onCancelClick = {},
        )
    }
}