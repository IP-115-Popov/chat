package com.eltex.chat.ui.theme

import androidx.compose.ui.graphics.Color

val basicLightPalette = BasicPalette(
    white = getColorFormHex("#FFFFFF"),
    white1 = getColorFormHex("#EDF4F7"),
    white2 = getColorFormHex("#F2F3F5"),
    lightGrey = getColorFormHex("#CBCBCC"),
    grey = getColorFormHex("#868686"),
    grey2 = getColorFormHex("#B8B8B8"),
    blue = getColorFormHex("#1B416B"),
    lightBlue = getColorFormHex("#329BDE"),
    lightGreen = Color.Green,
    black0 = Color.Black,
    black = getColorFormHex("#1B416B"),
    darkGray = getColorFormHex("#1F1F1F"),
    lightGrayBlue = getColorFormHex("#93B8CD"),
    aquamarine = getColorFormHex("#25CBA3"),
    red = getColorFormHex("#FF5F6D"),
    yellow = getColorFormHex("#EAC842"),
)

fun getColorFormHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}