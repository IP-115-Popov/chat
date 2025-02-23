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
import androidx.compose.material3.Button
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
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun MediaGrid() {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    val state = chatViewModel.state.collectAsState()

    val context = LocalContext.current
    var imageUris by remember { mutableStateOf(listOf<Uri>()) }
    var permissionGranted by remember { mutableStateOf(false) }

    // Проверяем, есть ли разрешение. В Android 13 нужно READ_MEDIA_IMAGES, в остальных READ_EXTERNAL_STORAGE
    val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    permissionGranted = ContextCompat.checkSelfPermission(
        context,
        permissionToCheck
    ) == PackageManager.PERMISSION_GRANTED

    // Launcher для запроса разрешения
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                permissionGranted = true
                imageUris = getAllImages(context) // Загружаем изображения, если разрешение получено
            } else {
                // Обработка случая, когда разрешение не предоставлено
                println("Разрешение не предоставлено")
            }
        }
    )

    // Загружаем изображения при первом запуске или когда разрешение получено
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            imageUris = getAllImages(context)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!permissionGranted) {
            Text("Требуется разрешение для доступа к галерее.")
            Button(onClick = {
                requestPermissionLauncher.launch(permissionToCheck)
            }) {
                Text("Запросить разрешение")
            }
        } else {
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
    Box(modifier = Modifier.padding(2.dp)) {
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
                onCheckedChange = {
                    if (selectable) onRemove()
                    else onSelected()
                },
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

@Composable
fun CheckableCircle(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    val borderColor = CustomTheme.basicPalette.white // Цвет границы всегда белый
    val backgroundColor =
        if (isChecked) CustomTheme.basicPalette.lightBlue else Color.Transparent // Прозрачный фон если не выбран
    val iconColor = CustomTheme.basicPalette.white

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
            .clickable(enabled = enabled) {
                onCheckedChange(!isChecked)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreviewGalleryGrid() {
    MaterialTheme {
        MediaGrid()
    }
}