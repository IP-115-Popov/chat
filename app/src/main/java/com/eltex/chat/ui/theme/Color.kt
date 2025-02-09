package com.eltex.chat.ui.theme

import androidx.compose.ui.graphics.Color

val basicLightPalette = BasicPalette(
    white = getColorFormHex("#FFFFFF"),
    white1 = getColorFormHex("#EDF4F7"),
    lightGrey = getColorFormHex("#CBCBCC"),
    grey = getColorFormHex("#868686"),
    black = getColorFormHex("#1B416B"),
    lightBlue = getColorFormHex("#329BDE"),
    lightGreen = Color.Green,
    blue = getColorFormHex("#1B416B")
)

fun getColorFormHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}