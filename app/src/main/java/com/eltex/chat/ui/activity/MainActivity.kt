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

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var initializedViewModel = MutableStateFlow(false);
    private val route = MutableStateFlow<NavRoutes>(NavRoutes.Splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TransparentSystemBars()
            CustomTheme {
                val routeState = route.collectAsState()
                val initializedViewModelState by initializedViewModel.collectAsState()
                val navController = rememberNavController()

                if (initializedViewModelState) {
                    NavigationGraph(
                        navController = navController,
                        startDestination = NavRoutes.Main,
                        mainViewModel = mainViewModel,
                    )
                    LaunchedEffect(routeState.value) {
                        if (routeState.value != NavRoutes.Splash) {
                            navController.navigate(routeState.value.route) {
                                popUpTo(NavRoutes.Splash.route) { inclusive = true }
                            }
                        }
                    }
                } else {
                    SplashScreen()
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                syncAuthDataUseCase().onRight {
                    delay(1000L)
                    route.value = NavRoutes.Main
                }.onLeft {
                    delay(1000L)
                    route.value = NavRoutes.Authorization
                }
            } catch (e: Exception) {
                delay(1000L)
                route.value = NavRoutes.Authorization
            } finally {
                initializedViewModel.value = true;
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
            window.navigationBarColor = Color.Transparent.toArgb()
        }
    }
}