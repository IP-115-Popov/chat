package com.eltex.chat.feature.chat.ui.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun BigMessageItem(
    title: String,
    text: String,
    time: String,
    messageUiModel: MessageUiModel,
    modifier: Modifier,
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .height(IntrinsicSize.Max)
            .background(
                color = CustomTheme.basicPalette.white2,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(shape = RoundedCornerShape(15.dp))
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .padding(top = 8.dp, end = 8.dp, start = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                style = CustomTheme.typographySfPro.caption1Medium,
                color = CustomTheme.basicPalette.lightBlue,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        if (messageUiModel.fileModel is FileModel.Img) {
            Spacer(Modifier.size(8.dp))
        }

        CallerMessage(
            text = text, time = time, messageUiModel = messageUiModel,
            modifier = Modifier,
        )
    }
}
