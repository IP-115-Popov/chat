package com.eltex.chat.feature.createchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltex.chat.R
import com.eltex.chat.feature.createchat.model.UserUiModel
import com.eltex.chat.feature.createchat.ui.components.ContactItem
import com.eltex.chat.feature.createchat.ui.components.SearchField
import com.eltex.chat.feature.createchat.viewmodel.CreateChatViewModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomCreatedChatScreen(
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
    val createChatViewModel = hiltViewModel<CreateChatViewModel>()
    val state = createChatViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp), contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        Modifier
                            .size(width = 36.dp, height = 5.dp)
                            .background(
                                CustomTheme.basicPalette.lightGrey,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )
                }
                Spacer(Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                ) {
                    TextButton(
                        onClick = { coroutineScope.launch { modalBottomSheetState.hide() } },
                        modifier = Modifier.align(Alignment.CenterStart),
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = CustomTheme.basicPalette.lightBlue,
                            style = CustomTheme.typographySfPro.bodyMedium500
                        )
                    }
                    Text(
                        text = stringResource(R.string.new_chat),
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = CustomTheme.basicPalette.black,
                        style = CustomTheme.typographySfPro.titleMedium
                    )
                }

                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Spacer(Modifier.size(8.dp))
                    SearchField(
                        value = state.value.searchValue,
                        placeholderText = stringResource(R.string.chat_search_placeholder),
                        onValueChange = { createChatViewModel.setSearchValue(it) },
                        onClearClick = { createChatViewModel.setSearchValue("") },
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Контакты",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.height(20.dp),
                    style = CustomTheme.typographySfPro.textMedium,
                    color = CustomTheme.basicPalette.grey
                )
                Spacer(Modifier.size(12.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.value.userList) { contact ->
                        ContactItem(contact = contact, onSelect = { it ->
                            createChatViewModel.onContactSelected(it)
                            coroutineScope.launch { modalBottomSheetState.hide() }
                        })
                    }
                }
            }
        }) {
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun BottomSheetScreenPreview() {
    CustomTheme {
        val coroutineScope = rememberCoroutineScope()
        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
                confirmStateChange = { true })
        BottomCreatedChatScreen(
            modalBottomSheetState = modalBottomSheetState,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (modalBottomSheetState.isVisible) {
                                    modalBottomSheetState.hide()
                                } else {
                                    modalBottomSheetState.show()
                                }
                            }
                        }, modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Открыть панель")
                    }
                }
            })
    }
}