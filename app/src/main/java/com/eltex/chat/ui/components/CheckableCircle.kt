package com.eltex.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun CheckableCircle(
    isChecked: Boolean,
    borderColor: Color,
    iconColor: Color = CustomTheme.basicPalette.white,
    onClick: () -> Unit = {},
) {
    val backgroundColor =
        if (isChecked) CustomTheme.basicPalette.lightBlue else Color.Transparent // Прозрачный фон если не выбран

    val border: Modifier = Modifier.border(
        width = 1.dp,
        color = borderColor,
        shape = CircleShape
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .then(if (!isChecked) border else Modifier)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Checked",
                tint = iconColor,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}