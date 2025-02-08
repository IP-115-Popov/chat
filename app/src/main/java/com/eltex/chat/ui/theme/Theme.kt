package com.eltex.chat.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CustomTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBasicPalette provides basicLightPalette,
        LocalCustomTypography provides simpleRobotoTypography,
        LocalCustomTypography provides simpleSfProDisplayTypography,
        content = content
    )
}