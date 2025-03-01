package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.feature.chat.ui.screens.ImageItem
import com.eltex.chat.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedAlertDialog(
    onDismissRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
    onSelectedRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .size(width = 228.dp, height = 88.dp)
                .background(
                    color = CustomTheme.basicPalette.white, shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onSelectedRequest()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.select),
                    color = CustomTheme.basicPalette.darkGray,
                    style = CustomTheme.typographySfPro.bodyMedium,
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle),
                    tint = CustomTheme.basicPalette.grey,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onDeleteRequest()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = stringResource(R.string.delete),
                    style = CustomTheme.typographySfPro.bodyMedium,
                    color = CustomTheme.basicPalette.red,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    tint = CustomTheme.basicPalette.red,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedAlertDialogPreview() {
    CustomTheme {
        Box(Modifier.fillMaxSize()) {
            SelectedAlertDialog(
                onDismissRequest = {},
                onDeleteRequest = {},
                onSelectedRequest = {},
            )
        }
    }
}