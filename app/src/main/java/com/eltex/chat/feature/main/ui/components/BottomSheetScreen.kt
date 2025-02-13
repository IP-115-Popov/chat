package com.eltex.chat.feature.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eltex.chat.R
import com.eltex.chat.feature.main.models.UserUiModel
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.chat.utils.getInitials
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomCreatedChatScreen(
    onContactClick: (UserUiModel) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchClearClick: () -> Unit,
    contacts: List<UserUiModel>,
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
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
                        value = searchValue,
                        placeholderText = stringResource(R.string.chat_search_placeholder),
                        onValueChange = onSearchValueChange,
                        onClearClick = onSearchClearClick,
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
                    items(contacts) { contact ->
                        ContactItem(contact = contact)
                        onContactClick(contact)
                    }
                }
            }
        }) {
        content()
    }
}

@Composable
fun ContactItem(
    contact: UserUiModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(CustomTheme.basicPalette.lightBlue, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.name.getInitials(),
                style = CustomTheme.typographySfPro.titleMedium,
                color = CustomTheme.basicPalette.white,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = contact.name, style = CustomTheme.typographySfPro.textSemibold)
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
        BottomCreatedChatScreen(onContactClick = {},
            searchValue = "",
            onSearchValueChange = {},
            onSearchClearClick = {},
            contacts = emptyList(),
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