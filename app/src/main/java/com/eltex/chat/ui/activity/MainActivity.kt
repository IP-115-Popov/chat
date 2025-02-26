package com.eltex.chat.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.eltex.chat.feature.main.viewmodel.MainViewModel
import com.eltex.chat.feature.navigationBar.NavRoutes
import com.eltex.chat.feature.navigationBar.NavigationGraph
import com.eltex.chat.ui.components.SplashScreen
import com.eltex.chat.ui.theme.CustomTheme
import com.eltex.domain.usecase.SyncAuthDataUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var syncAuthDataUseCase: SyncAuthDataUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TransparentSystemBars()
            CustomTheme {
                var isRegister by rememberSaveable { mutableStateOf(false) }
                var initializedViewModel by rememberSaveable { mutableStateOf(false) }
                val navController = rememberNavController()

                if (initializedViewModel) {
                    NavigationGraph(
                        navController = navController,
                        startDestination = NavRoutes.Main,
                    )
                    if(!isRegister) {
                        navController.navigate(NavRoutes.Authorization.route) {
                            launchSingleTop = true
                        }
                    }
                } else {
                    SplashScreen()
                }

                LaunchedEffect(Unit) {
                    runCatching {
                        syncAuthDataUseCase().onRight {
                            isRegister = true
                        }
                    }
                    delay(1000L)
                    initializedViewModel  = true;
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