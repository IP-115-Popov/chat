package com.eltex.chat.feature.navigationBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eltex.chat.feature.authorization.screens.SignInScreen
import com.eltex.chat.feature.exit.screens.ProfileExitScreen
import com.eltex.chat.ui.components.SplashScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Authorization.route) { SignInScreen() }
        composable(NavRoutes.Exit.route) { ProfileExitScreen() }
        composable(NavRoutes.Chat.route) { ProfileExitScreen() }
        composable(NavRoutes.Profile.route) { ProfileExitScreen() }
        composable(NavRoutes.Splash.route) {
            SplashScreen(
                navController = navController,
                nextRoute = NavRoutes.Authorization,
                displayTimeMillis = 1000L,
            )
        }
    }
}