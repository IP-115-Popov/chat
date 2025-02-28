package com.eltex.chat.feature.chat.ui.screens

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.ui.components.CheckableCircle
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MediaGrid() {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state = chatViewModel.state.collectAsState()

    val context = LocalContext.current
    var imageUris by remember { mutableStateOf(listOf<Uri>()) }
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    permissionGranted = ContextCompat.checkSelfPermission(
        context,
        permissionToCheck
    ) == PackageManager.PERMISSION_GRANTED

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            if (isGranted) {
                imageUris = getAllImages(context)
            } else {
                println("Разрешение не предоставлено")
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (!permissionGranted) {
            requestPermissionLauncher.launch(permissionToCheck)
        } else {
            imageUris = getAllImages(context)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (permissionGranted) {
            if (imageUris.isEmpty()) {
                Text("В галерее нет изображений.")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(imageUris.size) { index ->
                        val uri = imageUris[index]
                        ImageItem(
                            uri = uri,
                            selectable = state.value.attachmentUriList?.contains(uri) ?: false,
                            onSelected = { chatViewModel.addAttachmentUri(uri) },
                            onRemove = { chatViewModel.removeAttachmentUri(uri) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(
    selectable: Boolean,
    uri: Uri,
    onSelected: () -> Unit,
    onRemove: () -> Unit
) {
    Box(modifier = Modifier
        .padding(2.dp)
        .clickable {
            if (selectable) onRemove()
            else onSelected()
        }) {
        Image(
            painter = rememberImagePainter(
                data = uri,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "Image",
            modifier = Modifier
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 4.dp)
        ) {
            CheckableCircle(
                isChecked = selectable,
                borderColor = CustomTheme.basicPalette.white
            )
        }
    }
}

fun getAllImages(context: Context): List<Uri> {
    val imageUris = mutableListOf<Uri>()

    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    val projection = arrayOf(
        MediaStore.Images.Media._ID
    )

    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    val query = context.contentResolver.query(
        collection,
        projection,
        null,
        null,
        sortOrder
    )

    query?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            imageUris.add(contentUri)
        }
    }

    return imageUris
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewGalleryGrid() {
    MaterialTheme {
        MediaGrid()
    }
}