package com.eltex.chat.feature.navigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.R
import com.eltex.chat.ui.theme.CustomTheme

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .background(CustomTheme.basicPalette.white), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavBarItem(imageVector = ImageVector.vectorResource(R.drawable.ic_chat_bubble),
                    text = "Чат",
                    isSelected = currentRoute == NavRoutes.Main.route,
                    onClick = {
                        navController.navigate(NavRoutes.Main.route) {
                            launchSingleTop = true
                        }
                    })
                NavBarItem(imageVector = ImageVector.vectorResource(R.drawable.ic_account_circle),
                    text = "Профиль",
                    isSelected = currentRoute == NavRoutes.Profile.route,
                    onClick = {
                        navController.navigate(NavRoutes.Profile.route) {
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
}

@Composable
private fun NavBarItem(
    imageVector: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val color =
        if (isSelected) CustomTheme.basicPalette.lightBlue else CustomTheme.basicPalette.lightGrey

    Column(
        modifier = Modifier
            .size(width = 75.dp, height = 50.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = imageVector, contentDescription = null, tint = color
        )
        Text(
            text = text,
            color = color,
            style = CustomTheme.typographySfPro.caption2Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BottomNavigationBar() {
    CustomTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController())
            },
        ) { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) { }
        }
    }
}