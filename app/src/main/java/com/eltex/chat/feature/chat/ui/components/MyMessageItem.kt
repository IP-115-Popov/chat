package com.eltex.chat.feature.chat.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun MyMessageItem(
    text: String, time: String, read: Boolean, fileModel: FileModel? = null, bitmap: Bitmap? = null
) {
    Column(
        modifier = Modifier
            .background(
                color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp), horizontalAlignment = Alignment.Start

    ) {
        AttachmentItem(fileModel, bitmap)

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
            Spacer(Modifier.size(1.93.dp))
            Icon(
                imageVector = if (read) ImageVector.vectorResource(R.drawable.ic_done_all) else ImageVector.vectorResource(
                    R.drawable.ic_done
                ), contentDescription = null, tint = CustomTheme.basicPalette.lightBlue
            )
        }
    }
}

@Preview
@Composable
fun MyMessageItemPreview() {
    CustomTheme {
        Column(Modifier.verticalScroll(rememberScrollState(0))) {
            MyMessageItem(
                read = false,
                time = "15:50",
                text = "Привет! Это пример сообщения, которое может быть длинным и переноситься на несколько строк."
            )
            Spacer(Modifier.size(4.dp))
            MyMessageItem(
                read = true,
                time = "15:50",
                text = "п",
            )
            Spacer(Modifier.size(4.dp))
            MyMessageItem(
                read = false,
                time = "15:50",
                text = "привет",
            )
            Spacer(Modifier.size(4.dp))
            MyMessageItem(
                read = true, time = "15:50", text = "Короткое сообщение."
            )
            Spacer(Modifier.size(4.dp))
            MyMessageItem(
                read = false,
                time = "15:50",
                text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!",
            )
            Spacer(Modifier.size(4.dp))
            MyMessageItem(
                read = true,
                time = "15:50",
                text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!".repeat(
                    10
                ) + "1",
            )
        }
    }
}