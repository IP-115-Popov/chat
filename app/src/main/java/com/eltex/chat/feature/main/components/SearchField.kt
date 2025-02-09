package com.eltex.chat.feature.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun SearchField(
    value: String,
    placeholderText: String,
    onValueChange: (it: String) -> Unit,
    containerModifier: Modifier = Modifier,
    onClearClick: () -> Unit,
) {
    Row(
        Modifier
            .background(CustomTheme.basicPalette.white, shape = RoundedCornerShape(8.dp))
            .height(36.dp)
            .border(
                width = 0.5.dp,
                color = CustomTheme.basicPalette.lightGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .then(containerModifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(3.6.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
            contentDescription = null,
            tint = CustomTheme.basicPalette.grey
        )
        Spacer(Modifier.size(8.dp))
        Box(
            modifier = Modifier.weight(1f, fill = false),
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                textStyle = CustomTheme.typographyRoboto.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = CustomTheme.typographyRoboto.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = CustomTheme.basicPalette.grey,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (value.isNotEmpty()) {
            Spacer(Modifier.width(12.dp))
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                contentDescription = null,
                tint = CustomTheme.basicPalette.grey,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SearchFieldPreview() {
    CustomTheme {
        SearchField(
            value = "",
            placeholderText = "Поиск по чатам",
            onValueChange = {},
            onClearClick = {},
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SearchFieldPreview2() {
    CustomTheme {
        SearchField(
            value = "ПоискпочатамПоискпочатамПоискпочатамПоискпочатамПоискпочатамПоискпочатам",
            placeholderText = "Поиск по чатам",
            onValueChange = {},
            onClearClick = {},
        )
    }
}

@Preview(showBackground = false)
@Composable
fun SearchFieldPreview3() {
    CustomTheme {
        SearchField(
            value = "",
            placeholderText = "ПоискпочатамПоискпочатамПоискпочатамПоискпочатамПоискпочатамПоискпочатам",
            onValueChange = {},
            onClearClick = {},
        )
    }
}
