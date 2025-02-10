package com.eltex.chat.feature.authorization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSignInAlertDialog(
    message: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .width(274.dp)
                .wrapContentHeight()
                .background(
                    color = CustomTheme.basicPalette.white, shape = RoundedCornerShape(16.dp)
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    style = CustomTheme.typographySfPro.headlineSemibold,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HorizontalDivider()
            TextButton(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.ok), color = CustomTheme.basicPalette.lightBlue)
            }
        }
    }
}

@Preview
@Composable
fun ExitAlertDialogPreview() {
    CustomTheme {
        ErrorSignInAlertDialog(
            message = stringResource(R.string.log_out_of_account),
            onDismissRequest = {},
        )
    }
}

@Preview
@Composable
fun ExitAlertDialogPreview2() {
    CustomTheme {
        ErrorSignInAlertDialog(
            message = "stringResourcestringResourcestringResourcestringResourcestringResourcestringResourcestringResource",
            onDismissRequest = {},
        )
    }
}