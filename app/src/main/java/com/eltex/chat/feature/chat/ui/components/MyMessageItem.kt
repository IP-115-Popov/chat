package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.eltex.chat.R
import com.eltex.chat.feature.chat.model.MessageUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun MyMessageItem(
    text: String, time: String, read: Boolean, messageUiModel: MessageUiModel,
    modifier: Modifier,
) {
    if (text.isBlank() && (messageUiModel.fileModel is FileModel.Img || messageUiModel.fileModel is FileModel.Video)) {
        ConstraintLayout(
            modifier = Modifier
                .then(modifier)
                .background(
                    color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
                )
                .clip(shape = RoundedCornerShape(15.dp)),
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
                Spacer(Modifier.size(1.93.dp))
                Icon(
                    imageVector = if (read) ImageVector.vectorResource(R.drawable.ic_done_all) else ImageVector.vectorResource(
                        R.drawable.ic_done
                    ), contentDescription = null, tint = CustomTheme.basicPalette.lightBlue
                )
            }
        }
    } else {
        when (messageUiModel.fileModel) {
                is FileModel.Document -> {
                    Row (
                        modifier = Modifier
                            .then(modifier)
                            .background(
                                color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
                            )
                            .clip(shape = RoundedCornerShape(15.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box (modifier = Modifier.weight(1f, fill = false)) {
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
                            Spacer(Modifier.size(1.93.dp))
                            Icon(
                                imageVector = if (read) ImageVector.vectorResource(R.drawable.ic_done_all) else ImageVector.vectorResource(
                                    R.drawable.ic_done
                                ), contentDescription = null, tint = CustomTheme.basicPalette.lightBlue
                            )
                        }
                    }
                }
            else -> {
                ConstraintLayout(
                    modifier = Modifier
                        .then(modifier)
                        .background(
                            color = CustomTheme.basicPalette.white2, shape = RoundedCornerShape(15.dp)
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
                        Spacer(Modifier.size(1.93.dp))
                        Icon(
                            imageVector = if (read) ImageVector.vectorResource(R.drawable.ic_done_all) else ImageVector.vectorResource(
                                R.drawable.ic_done
                            ), contentDescription = null, tint = CustomTheme.basicPalette.lightBlue
                        )
                    }
                }
            }
        }

    }
}

//@Preview
//@Composable
//fun MyMessageItemPreview() {
//    CustomTheme {
//        Column(Modifier.verticalScroll(rememberScrollState(0))) {
//            MyMessageItem(
//                read = false,
//                time = "15:50",
//                text = "Привет! Это пример сообщения, которое может быть длинным и переноситься на несколько строк."
//            )
//            Spacer(Modifier.size(4.dp))
//            MyMessageItem(
//                read = true,
//                time = "15:50",
//                text = "п",
//            )
//            Spacer(Modifier.size(4.dp))
//            MyMessageItem(
//                read = false,
//                time = "15:50",
//                text = "привет",
//            )
//            Spacer(Modifier.size(4.dp))
//            MyMessageItem(
//                read = true, time = "15:50", text = "Короткое сообщение."
//            )
//            Spacer(Modifier.size(4.dp))
//            MyMessageItem(
//                read = false,
//                time = "15:50",
//                text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!",
//            )
//            Spacer(Modifier.size(4.dp))
//            MyMessageItem(
//                read = true,
//                time = "15:50",
//                text = "Еще одно очень длинное сообщение, чтобы протестировать перенос слов и максимальную ширину.  Просто очень очень длинное!".repeat(
//                    10
//                ) + "1",
//            )
//        }
//    }
//}