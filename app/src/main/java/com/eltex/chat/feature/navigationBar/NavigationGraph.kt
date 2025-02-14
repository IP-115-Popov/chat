package com.eltex.chat.feature.navigationBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eltex.chat.feature.chat.ui.screens.ChatScreen
import com.eltex.chat.feature.main.ui.screens.MainScreen
import com.eltex.chat.feature.profile.ui.screens.ProfileScreen
import com.eltex.chat.feature.signin.ui.screens.SignInScreen
import com.eltex.chat.ui.components.SplashScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Authorization.route) { SignInScreen(navController) }
        composable(NavRoutes.Main.route) { MainScreen(navController) }
        composable(NavRoutes.Profile.route) { ProfileScreen() }
        composable(NavRoutes.Profile.route+ "/{roomId}") { stackEntry ->
            val roomId = stackEntry.arguments?.getString("roomId")
            roomId?.let {
                ChatScreen(
                    navController = navController,
                    roomId = roomId,
                )
            }
        }
        composable(NavRoutes.Splash.route) {
            SplashScreen(
                navController = navController,
                nextRoute = NavRoutes.Authorization,
                displayTimeMillis = 1000L,
            )
        }
    }
}