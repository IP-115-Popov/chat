package com.eltex.chat.feature.chat.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun BigMessageItem(
    title: String,
    text: String,
    time: String,
    bitmap: Bitmap?,
    fileModel: FileModel?,
) {
    Column(
        modifier = Modifier
            .width(321.dp)
            .height(IntrinsicSize.Max)
            .background(
                color = CustomTheme.basicPalette.white2,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp)

    ) {
        Box(
            modifier = Modifier.height(20.dp),
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
        AttachmentItem(fileModel, bitmap)
        Text(
            text = text,
            textAlign = TextAlign.Start,
            style = CustomTheme.typographySfPro.bodyMedium,
            color = CustomTheme.basicPalette.black
        )
        Spacer(Modifier.size(4.dp))
        Box(
            Modifier
                .height(14.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = time,
                style = CustomTheme.typographySfPro.caption3Regular,
                color = CustomTheme.basicPalette.grey,
            )
        }

    }
}

//@Preview
//@Composable
//fun MessageItemPreview() {
//    CustomTheme {
//        Column(Modifier.verticalScroll(rememberScrollState(0))) {
//            BigMessageItem(
//                title = "Константин КонстантинопольскийКонстантин КонстантинопольскийКонстантин КонстантинопольскийКонстантин КонстантинопольскийКонстантин Константинопольский",
//                time = "15:50",
//                text = "Привет! Это пример сообщения, которое может быть длинным и переноситься на несколько строк."
//            )
//            Spacer(Modifier.size(4.dp))
//            BigMessageItem(
//                title = "Константин Константинопольский",
//                time = "15:50",
//                text = "прив",
//            )
//            Spacer(Modifier.size(4.dp))
//            BigMessageItem(
//                title = "Константин Константинопольский",
//                time = "15:50",
//                text = "Короткое сообщение."
//            )
//            Spacer(Modifier.size(4.dp))
//            BigMessageItem(
//                title = "Константин Константинопольский",
//                time = "15:50",
//                text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!",
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//fun MessageItemPreview2() {
//    CustomTheme {
//        BigMessageItem(
//            title = "Константин Константинопольский",
//            time = "15:50",
//            text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!".repeat(
//                10
//            ) + "1",
//        )
//    }
//}