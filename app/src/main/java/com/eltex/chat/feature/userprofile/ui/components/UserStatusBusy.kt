package com.eltex.chat.feature.userprofile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
fun UserStatusBusy(modifier: Modifier) {
    Box(
        Modifier
            .size(24.dp)
            .background(
                color = CustomTheme.basicPalette.red,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = CustomTheme.basicPalette.grey,
                shape = CircleShape
            )
            .then(modifier)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
            tint = CustomTheme.basicPalette.white,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Center),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserStatusBusyPreview() {
    CustomTheme {
        UserStatusBusy(modifier = Modifier)
    }
}
