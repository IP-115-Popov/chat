package com.eltex.chat.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class BasicPalette(
    val white: Color,
    val white1: Color,
    val lightGrey: Color,
    val grey: Color,
    val black: Color,
    val lightBlue: Color,
    val lightGreen: Color,
    val blue: Color,
)

data class CustomTypography(
    val titleMedium: TextStyle,
    val bodyMedium: TextStyle,
    val headlineSemibold: TextStyle,
)

object CustomTheme {
    val basicPalette: BasicPalette
        @Composable get() = LocalBasicPalette.current
    val typographyRoboto: CustomTypography
        @Composable get() = LocalCustomTypography.current
    val typographySfPro: CustomTypography
        @Composable get() = LocalCustomTypography.current
}

val LocalBasicPalette = staticCompositionLocalOf<BasicPalette> {
    error("No colors provided")
}
val LocalCustomTypography = staticCompositionLocalOf<CustomTypography> {
    error("No typography provided")
}