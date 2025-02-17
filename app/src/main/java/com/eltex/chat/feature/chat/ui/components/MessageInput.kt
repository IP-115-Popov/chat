package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MessageInput(
    value: String,
    onValueChange: (String)->Unit,
    onAttachClick: ()->Unit,
    onSendClick: ()->Unit,
) {
    var showAttachmentButtons by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (textField, buttons) = createRefs()

            Box(
                modifier = Modifier
                    .heightIn(min = 50.dp, max = 126.dp)
                    .verticalScroll(scrollState)
                    .constrainAs(textField) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(buttons.start, margin = 4.dp)
                        width = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = {
                        onValueChange(it)
                        showAttachmentButtons = it.isEmpty()
                    },
                    modifier = Modifier
                        .padding(vertical = 13.dp)
                        .fillMaxWidth(),
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            println("Сообщение отправлено: $value")
                            onSendClick()
                            onValueChange("")
                        }
                    )
                )

                if (value.isEmpty()) {
                    Text(
                        text = "Текст сообщения",
                        style = CustomTheme.typographyRoboto.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .alpha(0.6f)
                            .align(Alignment.CenterStart),
                        color = CustomTheme.basicPalette.grey
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(buttons) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showAttachmentButtons) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_attach_file),
                        contentDescription = "Прикрепить файл",
                        tint = CustomTheme.basicPalette.lightBlue,
                        modifier = Modifier.size(24.dp).clickable {
                            onAttachClick()
                        }
                    )
                    Spacer(Modifier.size(12.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard_voice),
                        contentDescription = "Голосовое сообщение",
                        tint = CustomTheme.basicPalette.lightBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.size(12.dp))
                }
                Box(modifier = Modifier
                    .size(32.dp)
                    .background(color =CustomTheme.basicPalette.lightBlue, shape = CircleShape)
                    .padding(start = 8.dp, end = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_send),
                        contentDescription = "Отправить",
                        tint = CustomTheme.basicPalette.white,
                        modifier = Modifier.size(height = 16.dp, width = 19.dp).clickable {
                            onSendClick()
                        }
                    )
                }
            }
        }
    }
}


@Preview( showBackground = true)
@Composable
fun MessageInputPreview() {
    var text by remember { mutableStateOf("") }
    CustomTheme {
        MessageInput(
            value = text,
            onValueChange = {text = it},
            onAttachClick = {text = "attach"},
            onSendClick = {text = "send"},
        )
    }
}
