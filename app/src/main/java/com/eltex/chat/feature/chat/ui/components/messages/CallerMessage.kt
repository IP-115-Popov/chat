package com.eltex.chat.feature.chat.ui.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun CallerMessage(
    text: String, time: String, messageUiModel: MessageUiModel,
    modifier: Modifier,
) {
    if (text.isBlank() && (messageUiModel.fileModel is FileModel.Img || messageUiModel.fileModel is FileModel.Video)) {
        ConstraintLayout(
            modifier = Modifier
                .then(modifier)
                .background(
                    color = CustomTheme.basicPalette.white2
                ),
        ) {
            val (attachment, timeRefs) = createRefs()
            Box(modifier = Modifier.constrainAs(attachment) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                AttachmentItem(messageUiModel)
            }

            Row(
                modifier = Modifier
                    .height(14.dp)
                    .constrainAs(timeRefs) {
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                    },
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    text = time,
                    style = CustomTheme.typographySfPro.caption3Regular,
                    color = CustomTheme.basicPalette.grey,
                )
            }
        }
    } else {
        when (messageUiModel.fileModel) {
            is FileModel.Document -> {
                Row(
                    modifier = Modifier
                        .then(modifier)
                        .background(
                            color = CustomTheme.basicPalette.white2,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(shape = RoundedCornerShape(15.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f, fill = false)) {
                        AttachmentItem(messageUiModel)
                    }
                    Row(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(14.dp),
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

            else -> {
                ConstraintLayout(
                    modifier = Modifier
                        .then(modifier)
                        .background(
                            color = CustomTheme.basicPalette.white2,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(shape = RoundedCornerShape(15.dp)),
                ) {
                    val (attachment, texteRefs, timeRefs) = createRefs()
                    Box(modifier = Modifier.constrainAs(attachment) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                        AttachmentItem(messageUiModel)
                    }
                    Text(text = text,
                        textAlign = TextAlign.Start,
                        style = CustomTheme.typographySfPro.bodyMedium,
                        color = CustomTheme.basicPalette.black,
                        modifier = Modifier
                            .widthIn(min = 106.dp)
                            .padding(end = 8.dp)
                            .constrainAs(texteRefs) {
                                top.linkTo(attachment.bottom, margin = 8.dp)
                                bottom.linkTo(timeRefs.top)
                                start.linkTo(parent.start, margin = 8.dp)
                            })

                    Row(
                        modifier = Modifier
                            .height(14.dp)
                            .constrainAs(timeRefs) {
                                top.linkTo(texteRefs.bottom)
                                bottom.linkTo(parent.bottom, margin = 8.dp)
                                end.linkTo(parent.end, margin = 8.dp)
                            },
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
        }

    }
}