package com.eltex.chat.feature.userprofile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun WriteButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp)
            .background(
                CustomTheme.basicPalette.white, RoundedCornerShape(10.dp)
            )
            .padding(start = 16.dp, end = 16.15.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.write),
            style = CustomTheme.typographySfPro.headlineSemibold,
            color = CustomTheme.basicPalette.darkGray
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            modifier = Modifier
                .height(13.33.dp)
                .padding(end = 1.dp),
            tint = CustomTheme.basicPalette.lightGrey
        )
    }
}

@Preview
@Composable
fun WriteButtonPreview() {
    CustomTheme {
        WriteButton({})
    }
}