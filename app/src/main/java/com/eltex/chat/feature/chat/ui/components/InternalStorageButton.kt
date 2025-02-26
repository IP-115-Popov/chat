package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun InternalStorageButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable {
                        onClick()
                    }
                    .padding(vertical = 20.dp)
                    .padding(start = 16.dp)
                    .height(24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = CustomTheme.basicPalette.lightBlue,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_storage),
                        contentDescription = null,
                        tint = CustomTheme.basicPalette.white,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.internal_storage),
                    color = CustomTheme.basicPalette.lightBlue,
                    style = CustomTheme.typographySfPro.headlineSemibold
                )
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InternalStorageButtonPreview() {
    CustomTheme {
        InternalStorageButton(onClick = {})
    }
}