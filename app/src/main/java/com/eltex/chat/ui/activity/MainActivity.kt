package com.eltex.chat.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.navigationBar.BottomNavigationBar
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.feature.navigationBar.NavigationGraph
import com.eltex.chat.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CustomTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute != NavRoutes.Splash.route && currentRoute != NavRoutes.Authorization.route) {
            BottomNavigationBar(navController)
        }
    }) { innerPadding ->
        NavigationGraph(
            navController = navController, modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomTheme {
        Main()
    }
}