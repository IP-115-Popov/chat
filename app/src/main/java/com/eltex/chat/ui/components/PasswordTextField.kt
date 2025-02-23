package com.eltex.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun PasswordTextField(
    value: String,
    placeholder: String,
    onValueChange: (it: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .then(modifier)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = CustomTheme.basicPalette.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 0.5.dp,
                    color = CustomTheme.basicPalette.lightGrey,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = CustomTheme.typographySfPro.bodyMedium,
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = CustomTheme.typographySfPro.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .alpha(0.6f)
                    .align(Alignment.CenterStart),
                color = CustomTheme.basicPalette.grey
            )
        }
    }
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    SimpleTextField(
        value = "",
        placeholder = stringResource(R.string.login),
        onValueChange = {},
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}