package com.eltex.chat.feature.chat.ui.components.bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun DeleteMessageBottomBar(
    enabled: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .size(44.dp)
            .align(Alignment.Center)
            .clickable {
                if (enabled) {
                    onClick()
                }
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                contentDescription = null,
                tint = if (enabled) CustomTheme.basicPalette.lightBlue else CustomTheme.basicPalette.lightGrey,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            modifier = Modifier.fillMaxWidth(),
            color = CustomTheme.basicPalette.lightGrey
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DeleteMessageBottomBarPreview() {
    CustomTheme {
        Scaffold(bottomBar = {
            DeleteMessageBottomBar(
                enabled = true,
                onClick = {}
            )
        }) { ip -> Box(
            Modifier
                .padding(ip)
                .fillMaxSize()) }
    }

}

@Preview
@Composable
fun DeleteMessageBottomBarPreview2() {
    CustomTheme {
        DeleteMessageBottomBar(
            enabled = false,
            onClick = {}
        )
    }
}