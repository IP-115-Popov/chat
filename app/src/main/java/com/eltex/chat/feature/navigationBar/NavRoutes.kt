package com.eltex.chat.feature.navigationBar

sealed class NavRoutes(val route: String) {
    object Authorization : NavRoutes("home")
    object Main : NavRoutes("about")
    object Profile : NavRoutes("profile")
    object Splash : NavRoutes("splash")
}