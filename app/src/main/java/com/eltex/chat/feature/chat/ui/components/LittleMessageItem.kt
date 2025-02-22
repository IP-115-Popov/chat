package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme


@Composable
fun LittleMessageItemLayout(
    title: @Composable () -> Unit, text: @Composable () -> Unit, time: @Composable () -> Unit
) {
    Layout(content = {
        title()
        text()
        time()
    }) { measurables, constraints ->
        val titleMeasurable = measurables[0]
        val textMeasurable = measurables[1]
        val timeMeasurable = measurables[2]

        // Измеряем текст сообщения и время
        val textPlaceable = textMeasurable.measure(constraints.copy(minWidth = 0))
        val timePlaceable = timeMeasurable.measure(constraints.copy(minWidth = 0))

        // Вычисляем общую ширину текста сообщения и времени
        val contentWidth = textPlaceable.width + timePlaceable.width

        // Измеряем заголовок с ограничением по ширине, равной ширине текста сообщения
        val titlePlaceable = titleMeasurable.measure(Constraints.fixedWidth(contentWidth))

        // Вычисляем высоту
        val height = titlePlaceable.height + textPlaceable.height

        layout(contentWidth, height) {
            titlePlaceable.placeRelative(x = 0, y = 0)
            textPlaceable.placeRelative(x = 0, y = titlePlaceable.height)
            timePlaceable.placeRelative(x = textPlaceable.width, y = titlePlaceable.height)
        }
    }
}

//Up to 33 characters
@Composable
fun LittleMessageItem(
    title: String,
    text: String,
    time: String,
    modifier: Modifier,
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .background(
                color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp)
    ) {
        LittleMessageItemLayout(title = {
            Box(
                modifier = Modifier.height(20.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = title,
                    style = CustomTheme.typographySfPro.caption1Medium,
                    color = CustomTheme.basicPalette.lightBlue,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }, text = {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = CustomTheme.typographySfPro.bodyMedium,
                color = CustomTheme.basicPalette.black,
                modifier = Modifier
                    .height(20.dp)
                    .widthIn(min = 69.dp)
            )
        }, time = {
            Box(
                Modifier
                    .height(20.dp)
                    .widthIn(min = 33.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = time,
                    style = CustomTheme.typographySfPro.caption3Regular,
                    color = CustomTheme.basicPalette.grey,
                )
            }
        })
    }
}


@Preview
@Composable
fun LittleMessageItemPreview() {
    CustomTheme {
        Column(Modifier.verticalScroll(rememberScrollState(0))) {
            LittleMessageItem(
                title = "Константин Константинопольский",
                time = "15:50",
                text = "прив",
                modifier = Modifier,
            )
            Spacer(Modifier.size(4.dp))
            LittleMessageItem(
                title = "Константин Константинопольский",
                time = "15:50",
                text = "Короткое0сообщение.КороткоеКоротк",
                modifier = Modifier,
            )
        }
    }
}
