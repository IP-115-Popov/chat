package com.eltex.chat.feature.userprofile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun UserStatusOnline(modifier: Modifier) {
    Box(
        Modifier
            .size(24.dp)
            .background(
                color = CustomTheme.basicPalette.aquamarine,
                shape = CircleShape
            )
            .then(modifier)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            tint = CustomTheme.basicPalette.white,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Center),
        )
    }
}

@Preview
@Composable
fun UserStatusOnlinePreview() {
    CustomTheme {
        UserStatusOnline(modifier = Modifier)
    }
}
