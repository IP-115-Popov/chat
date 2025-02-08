package com.eltex.chat.feature.navigationBar

sealed class NavRoutes(val route: String) {
    object Authorization : NavRoutes("home")
    object Exit : NavRoutes("contact")
    object Chat : NavRoutes("about")
    object Profile : NavRoutes("profile")
}