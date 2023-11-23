package com.anindita.otakuodyssey.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object All : Screen("all")
    object Profile : Screen("profile")
    object Favorite : Screen("favorite")
    object DetailAnime : Screen("all/{animeId}") {
        fun createRoute(animeId: String) = "all/$animeId"
    }
}
