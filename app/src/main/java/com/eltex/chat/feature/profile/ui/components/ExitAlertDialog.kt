package com.eltex.chat.feature.profile.ui.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitAlertDialog(
    onDismissRequest: () -> Unit,
    onExitRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .size(width = 274.dp, height = 122.dp)
                .background(
                    color = CustomTheme.basicPalette.white, shape = RoundedCornerShape(16.dp)
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.log_out_of_account),
                style = CustomTheme.typographySfPro.headlineSemibold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.are_you_sure_want_to_get_out),
                style = CustomTheme.typographySfPro.bodyMedium,
                color = CustomTheme.basicPalette.grey
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text(
                        stringResource(R.string.Cancel), color = CustomTheme.basicPalette.lightBlue
                    )
                }
                VerticalDivider()
                TextButton(onClick = {
                    onExitRequest()
                }) {
                    Text(stringResource(R.string.Exit), color = CustomTheme.basicPalette.lightBlue)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExitAlertDialogPreview() {
    CustomTheme {
        Box(Modifier.fillMaxSize()) {
            ExitAlertDialog(
                onDismissRequest = {},
                onExitRequest = {}
            )
        }
    }
}