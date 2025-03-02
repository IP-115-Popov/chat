package com.eltex.chat.feature.chat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun ChatSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
    ) { data ->
        Snackbar(
            backgroundColor = CustomTheme.basicPalette.blue,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    text = data.message,
                    color = CustomTheme.basicPalette.white,
                    style = CustomTheme.typographySfPro.bodyRegular,
                )
            }
        }
    }
}