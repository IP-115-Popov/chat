package com.eltex.chat.feature.infochat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.feature.infochat.models.MemberUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials

@Composable
fun MemberItem(
    member: MemberUiModel,
    drawHorizontalDivider: Boolean = true,
    onSelect: (MemberUiModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 10.dp)
            .clickable {
                onSelect(member)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (member.avatar != null) {
            Image(
                bitmap = member.avatar,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 6.dp)
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
                    text = member.name.getInitials(),
                    style = CustomTheme.typographySfPro.titleMedium,
                    color = CustomTheme.basicPalette.white,
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(Modifier.fillMaxSize()) {
            Text(
                text = member.name,
                style = CustomTheme.typographySfPro.bodyMedium,
                color = CustomTheme.basicPalette.darkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 16.dp).align(Alignment.CenterStart),
            )
            if (drawHorizontalDivider)
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MemberItemPreview(){
    CustomTheme{
        MemberItem(
            member = MemberUiModel(
                avatar = null,
                id = "",
                name = "Абобер №3",
                status = "online",
                username = "abober",
            ),
            onSelect = {},
            drawHorizontalDivider = false
        )
    }
}