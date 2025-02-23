package com.eltex.chat.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class BasicPalette(
    val white: Color,
    val white1: Color,
    val white2: Color,
    val lightGrey: Color,
    val grey: Color,
    val grey2: Color,
    val darkGray: Color,
    val black: Color,
    val lightGreen: Color,
    val blue: Color,
    val lightBlue: Color,
    val lightGrayBlue: Color,
)

data class CustomSfProDisplayTypography(
    val titleMedium: TextStyle,
    val titleLarge: TextStyle,
    val bodyMedium: TextStyle,
    val headlineSemibold: TextStyle,
    val caption2Medium: TextStyle,
    val caption2Regular: TextStyle,
    val bodyMedium500: TextStyle,
    val caption1Regular: TextStyle,
    val textSemibold: TextStyle,
    val textMedium: TextStyle,
    val caption3Regular: TextStyle,
    val caption1Medium: TextStyle,
)


object CustomTheme {
    val basicPalette: BasicPalette
        @Composable get() = LocalBasicPalette.current
    val typographySfPro: CustomSfProDisplayTypography
        @Composable get() = LocalCustomSfProDisplayTypography.current
}

val LocalBasicPalette = staticCompositionLocalOf<BasicPalette> {
    error("No colors provided")
}

val LocalCustomSfProDisplayTypography = staticCompositionLocalOf<CustomSfProDisplayTypography> {
    error("No CustomSfProDisplayTypography provided")
}