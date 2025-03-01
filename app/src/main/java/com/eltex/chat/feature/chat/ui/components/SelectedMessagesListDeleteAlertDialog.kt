package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedMessagesListDeleteAlertDialog(
    onDismissRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
    AlertDialog(
    onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .size(width = 274.dp, height = 122.dp)
                .background(
                    color = CustomTheme.basicPalette.white, shape = RoundedCornerShape(16.dp)
                ), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.question_delete_messages),
                style = CustomTheme.typographySfPro.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.action_cannot_undone),
                style = CustomTheme.typographySfPro.bodyMedium,
                textAlign = TextAlign.Center,
                color = CustomTheme.basicPalette.grey
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.height(44.dp).fillMaxWidth()
            ) {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(
                        text = stringResource(R.string.Cancel),
                        style = CustomTheme.typographySfPro.bodyRegular,
                        color = CustomTheme.basicPalette.lightBlue
                    )
                }
                VerticalDivider()
                TextButton(onClick = {
                    onDeleteRequest()
                }) {
                    Text(
                        text = stringResource(R.string.delete),
                        style = CustomTheme.typographySfPro.bodyRegular,
                        color = CustomTheme.basicPalette.red,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedMessagesListDeleteAlertDialogPreview() {
    CustomTheme {
        Box(Modifier.fillMaxSize()) {
            SelectedMessagesListDeleteAlertDialog(
                onDismissRequest = {},
                onDeleteRequest = {}
            )
        }
    }
}