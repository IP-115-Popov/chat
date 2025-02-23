package com.eltex.chat.feature.navigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun BottomBarShadow(modifier: Modifier = Modifier) {
    val shadowColor = CustomTheme.basicPalette.blue.copy(alpha = 0.08f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(shadowColor)
            .then(modifier)
    )
}