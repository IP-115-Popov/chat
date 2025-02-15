package com.eltex.chat.ui.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberLazyListStatePaginated(onLoadMore: () -> Unit): LazyListState {
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    val isAtBottom = remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 3
        }
    }

    LaunchedEffect(isAtBottom.value) {
        if (isAtBottom.value) {
            Log.d("PAGINATION", "Loading more...")
            onLoadMore()
        }
    }

    return listState
}