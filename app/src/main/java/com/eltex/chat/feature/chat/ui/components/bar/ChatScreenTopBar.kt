package com.eltex.chat.feature.chat.ui.components.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials

@Composable
fun ChatScreenTopBar(
    title: String,
    usersCount: Int,
    avatar: ImageBitmap?,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Column(Modifier.background(CustomTheme.basicPalette.blue)) {
        Box(
            Modifier
                .height(48.dp)
                .fillMaxWidth()
        )
        Row(
            Modifier
                .height(48.dp)
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
                Spacer(Modifier.size(12.dp))
                Column {
                    Text(
                        text = title,
                        style = CustomTheme.typographySfPro.titleMedium,
                        color = CustomTheme.basicPalette.white,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Text(
                        text = stringResource(R.string.participants) + usersCount.toString(),
                        style = CustomTheme.typographySfPro.caption2Medium,
                        color = CustomTheme.basicPalette.white,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
            Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_more_24),
                tint = CustomTheme.basicPalette.white,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onMoreClick()
                })
        }
    }
}

@Preview
@Composable
fun ChatScreenTopBarPreview() {
    CustomTheme {
        ChatScreenTopBar(
            title = "Константин Константин",
            usersCount = 3,
            avatar = null,
            {},
            {},
        )
    }
}

@Preview
@Composable
fun ChatScreenTopBarPreview2() {
    CustomTheme {
        ChatScreenTopBar(
            title = "КонстантинКонстантинКонстантинКонстантинКонстантинКонстантин",
            usersCount = 3,
            avatar = null,
            {},
            {},
        )
    }
}