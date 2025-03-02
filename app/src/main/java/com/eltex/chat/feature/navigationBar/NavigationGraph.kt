package com.eltex.chat.feature.navigationBar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eltex.chat.feature.chat.ui.screens.ChatScreen
import com.eltex.chat.feature.chat.viewmodel.ChatViewModel
import com.eltex.chat.feature.infochat.ui.screens.ChatInfoScreen
import com.eltex.chat.feature.main.ui.screens.MainScreen
import com.eltex.chat.feature.main.viewmodel.MainViewModel
import com.eltex.chat.feature.profile.ui.screens.ProfileScreen
import com.eltex.chat.feature.signin.ui.screens.SignInScreen
import com.eltex.chat.feature.userprofile.ui.screens.UserProfileScreen
import com.eltex.chat.ui.components.SplashScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: NavRoutes,
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val chatViewModel = hiltViewModel<ChatViewModel>()
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(NavRoutes.Authorization.route) { SignInScreen(navController) }
        composable(NavRoutes.Main.route) {
            MainScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(NavRoutes.Profile.route) { ProfileScreen(navController) }
        composable(NavRoutes.Chat.route + "/{roomId}" + "/{roomType}") { stackEntry ->
            val roomId = stackEntry.arguments?.getString("roomId")
            val roomType = stackEntry.arguments?.getString("roomType")
            if (roomId != null && roomType != null) {
                ChatScreen(
                    navController = navController,
                    roomId = roomId,
                    roomType = roomType,
                    chatViewModel = chatViewModel,
                )
            }
        }
        composable(NavRoutes.Splash.route) { SplashScreen() }
        composable(NavRoutes.ChatInfo.route + "/{roomId}" + "/{roomType}") { stackEntry ->
            val roomId = stackEntry.arguments?.getString("roomId")
            val roomType = stackEntry.arguments?.getString("roomType")
            if (roomId != null && roomType != null) {
                ChatInfoScreen(
                    navController = navController,
                    roomId = roomId,
                    roomType = roomType,
                )
            }
        }
        composable(NavRoutes.UserProfile.route + "/{userId}") { stackEntry ->
            stackEntry.arguments?.getString("userId")?.let { userId ->
                UserProfileScreen(
                    navController = navController,
                    userId = userId,
                )
            }
        }
    }
}