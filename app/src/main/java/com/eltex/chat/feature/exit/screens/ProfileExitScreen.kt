package com.eltex.chat.feature.exit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme


@Composable
fun ProfileExitScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.basicPalette.white1)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.subtract),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(142.dp),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .offset(y = -59.dp)
                .size(118.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "Татьяна Иванова",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (8 - 59).dp),
            style = CustomTheme.typographySfPro.titleMedium
        )

        Spacer(modifier = Modifier.height(190.dp))

        LogoutButton {
            println("Logout clicked")
        }

    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp)
            .background(
                CustomTheme.basicPalette.white,
                RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 11.dp)
                .padding(start = 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = CustomTheme.basicPalette.lightBlue
                )
                Spacer(modifier = Modifier.width(11.dp))
                Text(
                    text = "Выйти",
                    style = CustomTheme.typographySfPro.headlineSemibold
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward),
                contentDescription = null,
                modifier = Modifier
                    .height(13.33.dp)
                    .padding(end = 1.dp),
                tint = CustomTheme.basicPalette.lightGrey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CustomTheme {
        ProfileExitScreen()
    }
}