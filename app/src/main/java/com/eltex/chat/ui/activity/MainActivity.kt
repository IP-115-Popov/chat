package com.eltex.chat.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.feature.navigationBar.NavigationGraph
import com.eltex.chat.ui.components.SplashScreen
import com.eltex.chat.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TransparentSystemBars()
            CustomTheme {
                val mainActivityViewModel = hiltViewModel<MainActivityViewModel>()
                val state by mainActivityViewModel.state.collectAsState()
                val navController = rememberNavController()

                if (state.isLoadingEnd) {
                    NavigationGraph(
                        navController = navController,
                        startDestination = NavRoutes.Main,
                    )
                    if (!state.isUserRegistered) {
                        navController.navigate(NavRoutes.Authorization.route) {
                            launchSingleTop = true
                        }
                    }
                } else {
                    SplashScreen()
                }

                LaunchedEffect(Unit) {
                    mainActivityViewModel.syncAuthData()
                }
            }
        }
    }
}

@Composable
fun TransparentSystemBars() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.Transparent.toArgb()
        }
    }
}