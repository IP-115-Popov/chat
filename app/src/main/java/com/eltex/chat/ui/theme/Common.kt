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

data class CustomRobotoTypography(
    val titleMedium: TextStyle,
    val bodyMedium: TextStyle,
)

data class CustomSfProDisplayTypography(
    val titleMedium: TextStyle,
    val bodyMedium: TextStyle,
    val headlineSemibold: TextStyle,
    val caption2Medium: TextStyle,
    val bodyMedium500: TextStyle,
    val caption1Regular: TextStyle,
)


object CustomTheme {
    val basicPalette: BasicPalette
        @Composable get() = LocalBasicPalette.current
    val typographyRoboto: CustomRobotoTypography
        @Composable get() = LocalCustomTypography.current
    val typographySfPro: CustomSfProDisplayTypography
        @Composable get() = LocalCustomSfProDisplayTypography.current
}

val LocalBasicPalette = staticCompositionLocalOf<BasicPalette> {
    error("No colors provided")
}
val LocalCustomTypography = staticCompositionLocalOf<CustomRobotoTypography> {
    error("No typography provided")
}
val LocalCustomSfProDisplayTypography = staticCompositionLocalOf<CustomSfProDisplayTypography> {
    error("No CustomSfProDisplayTypography provided")
}