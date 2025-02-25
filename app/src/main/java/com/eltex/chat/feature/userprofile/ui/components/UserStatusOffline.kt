package com.eltex.chat.feature.userprofile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun UserStatusOffline(modifier: Modifier) {
    Box(
        Modifier
            .size(24.dp)
            .background(
                color = CustomTheme.basicPalette.white,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = CustomTheme.basicPalette.grey,
                shape = CircleShape
            )
            .then(modifier)
    )
}

@Preview
@Composable
fun UserStatusOfflinePreview() {
    CustomTheme {
        UserStatusOffline(modifier = Modifier)
    }
}
