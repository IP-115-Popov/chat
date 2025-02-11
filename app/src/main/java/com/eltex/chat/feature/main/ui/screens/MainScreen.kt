package com.eltex.chat.feature.main.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.eltex.chat.feature.main.ui.components.SearchField
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MainScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.height(48.dp).fillMaxWidth().background(CustomTheme.basicPalette.blue))
            Box(Modifier.height(44.dp).fillMaxWidth().background(CustomTheme.basicPalette.blue)){
                Text(
                    text = stringResource(R.string.chats),
                    style = CustomTheme.typographySfPro.titleMedium,
                    modifier = Modifier.align(Alignment.Center),
                    color = CustomTheme.basicPalette.white
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp).clickable {  },
                    tint = CustomTheme.basicPalette.white
                )

            }
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth()
                    .background(CustomTheme.basicPalette.blue)
                    .padding(horizontal = 16.dp)
            ) {
                SearchField(
                    value = "",
                    placeholderText = stringResource(R.string.chat_search_placeholder),
                    onValueChange = {},
                    onClearClick = {},
                )
            }
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
//                items() {
//                    ChatItem()
//                }
                }
            }
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    CustomTheme {
        MainScreen()
    }
}