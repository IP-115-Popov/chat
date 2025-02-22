package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun CallerMessage(
    text: String, time: String, messageUiModel: MessageUiModel,
    modifier: Modifier,
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .background(
                color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp), horizontalAlignment = Alignment.Start
    ) {
        AttachmentItem(messageUiModel)

        Text(
            text = text,
            textAlign = TextAlign.Start,
            style = CustomTheme.typographySfPro.bodyMedium,
            color = CustomTheme.basicPalette.black,
            modifier = Modifier
                .widthIn(min = 106.dp)
                .heightIn(min = 20.dp)
        )

        Row(
            modifier = Modifier
                .height(14.dp)
                .align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = time,
                style = CustomTheme.typographySfPro.caption3Regular,
                color = CustomTheme.basicPalette.grey,
            )
        }
    }
}