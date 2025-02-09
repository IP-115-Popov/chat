package com.eltex.chat.feature.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.feature.main.components.MainScreenTopBar
import com.eltex.chat.feature.main.components.SearchField
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MainScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {MainScreenTopBar(onAddClick = {})}
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .height(44.dp)
                    .fillMaxWidth()
                    .background(CustomTheme.basicPalette.blue)
                    .padding(horizontal = 16.dp)
            ) {
                SearchField(
                    value = "",
                    placeholderText = stringResource(R.string.chat_search_placeholder),
                    onValueChange = {}
                )
            }
            Column {
                LazyColumn(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()) {
//                items() {
//                    ChatItem()
//                }
                }
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun MainScreenPreview() {
    CustomTheme {
        MainScreen()
    }
}