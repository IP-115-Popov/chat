package com.eltex.chat.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eltex.chat.R

val Roboto = FontFamily(Font(R.font.roboto_regular))
private val robotoTextStyle = TextStyle(fontFamily = Roboto)

val sfProDisplay = FontFamily(Font(R.font.roboto_regular))
private val sfProDisplayTextStyle = TextStyle(fontFamily = sfProDisplay)

val simpleRobotoTypography = CustomRobotoTypography(
    titleMedium = robotoTextStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),

    bodyMedium = robotoTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    titleLarge = robotoTextStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 32.sp,
        lineHeight = 60.sp,
    ),
)

val simpleSfProDisplayTypography = CustomSfProDisplayTypography(
    titleMedium = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    textMedium = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    textSemibold = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodyMedium = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    headlineSemibold = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 17.sp,
        lineHeight = 24.sp,
    ),

    caption2Medium = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 11.sp,
        lineHeight = 16.sp,
    ),
    caption2Regular = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 11.sp,
        lineHeight = 16.sp,
    ),

    bodyMedium500 = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 17.sp,
        lineHeight = 24.sp,
    ),

    caption1Regular = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 13.sp,
        lineHeight = 20.sp,
    ),

    caption3Regular = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 9.sp,
        lineHeight = 14.sp,
    ),

    caption1Medium = sfProDisplayTextStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 13.sp,
        lineHeight = 20.sp,
    ),
)