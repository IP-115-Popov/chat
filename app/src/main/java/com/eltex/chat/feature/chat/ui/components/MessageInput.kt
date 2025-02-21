package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
    showSendButtons: Boolean,
    showAttachmentButtons: Boolean,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    onAttachClick: () -> Unit,
    onSendClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.basicPalette.white)
            .padding(start = 16.dp)
            .navigationBarsPadding()
            .imePadding()
    ) {
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
                    }, contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    enabled = enabled,
                    onValueChange = {
                        onValueChange(it)
                    },
                    modifier = Modifier
                        .padding(vertical = 13.dp)
                        .fillMaxWidth(),
                    maxLines = 5,
                )

                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.message_input_placeholder),
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
                modifier = Modifier.constrainAs(buttons) {
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }, verticalAlignment = Alignment.CenterVertically
            ) {
                if (showAttachmentButtons) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_attach_file),
                        contentDescription = null,
                        tint = CustomTheme.basicPalette.lightBlue,
                        modifier = Modifier
                            .size(24.dp)
                            .then(if (enabled) {
                                Modifier.clickable {
                                    onAttachClick()
                                }
                            } else {
                                Modifier
                            })

                    )
                    Spacer(Modifier.size(12.dp))
                }
                if (showSendButtons) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = CustomTheme.basicPalette.lightBlue, shape = CircleShape
                            )
                            .padding(start = 8.dp, end = 5.dp), contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_send),
                            contentDescription = null,
                            tint = CustomTheme.basicPalette.white,
                            modifier = Modifier
                                .size(height = 16.dp, width = 19.dp)
                                .then(if (enabled) {
                                    Modifier.clickable {
                                        onSendClick()
                                    }
                                } else {
                                    Modifier
                                })

                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessageInputPreview() {
    var text by remember { mutableStateOf("") }
    CustomTheme {
        MessageInput(
            value = text,
            showSendButtons = true,
            showAttachmentButtons = true,
            enabled = true,
            onValueChange = { text = it },
            onAttachClick = { text = "attach" },
            onSendClick = { text = "send" },
        )
    }
}
