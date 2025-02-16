package com.eltex.chat.feature.chat.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.models.FileModel

@Composable
fun AttachmentItem(
    fileModel: FileModel?,
    bitmap: Bitmap?
) {
    when (fileModel) {
        is FileModel.Document -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = CustomTheme.basicPalette.lightBlue, shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_file),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = CustomTheme.basicPalette.white
                    )
                }
                Spacer(Modifier.size(4.dp))
                Text(
                    text = fileModel.title ?: "Null",
                    style = CustomTheme.typographySfPro.bodyMedium,
                    color = CustomTheme.basicPalette.black
                )
            }
        }

        is FileModel.Img -> {

            bitmap?.let {
                Image(
                    modifier = Modifier
                        .widthIn(max = 239.dp)
                        .heightIn(max = 231.dp),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }

        is FileModel.Video -> {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = CustomTheme.basicPalette.lightBlue, shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_file),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = CustomTheme.basicPalette.white
                )
            }
            Spacer(Modifier.size(4.dp))
            Text(
                text = fileModel.format ?: "Null",
                style = CustomTheme.typographySfPro.bodyMedium,
                color = CustomTheme.basicPalette.black
            )
        }

        null -> {

        }
    }
}