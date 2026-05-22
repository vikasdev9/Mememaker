package com.example.mememaker.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Editor : Screen("editor?imagePath={imagePath}&gifUrl={gifUrl}") {
        fun createRoute(imagePath: String? = null, gifUrl: String? = null): String {
            return "editor?imagePath=$imagePath&gifUrl=$gifUrl"
        }
    }
    object GIFSearch : Screen("gif_search")
    object StickerSearch : Screen("sticker_search")
    object Settings : Screen("settings")
}
