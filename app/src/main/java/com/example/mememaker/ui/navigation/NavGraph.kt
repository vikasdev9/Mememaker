package com.example.mememaker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mememaker.ui.screens.editor.EditorScreen
import com.example.mememaker.ui.screens.gif.GifSearchScreen
import com.example.mememaker.ui.screens.home.HomeScreen
import com.example.mememaker.ui.screens.sticker.StickerSearchScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToEditor = { img, gif ->
                    navController.navigate(Screen.Editor.createRoute(img, gif))
                },
                onNavigateToGIFs = {
                    navController.navigate(Screen.GIFSearch.route)
                },
                onNavigateToStickers = {
                    navController.navigate(Screen.StickerSearch.route)
                }
            )
        }
        composable(
            route = Screen.Editor.route,
            arguments = listOf(
                navArgument("imagePath") { nullable = true },
                navArgument("gifUrl") { nullable = true }
            )
        ) {
            EditorScreen()
        }
        composable(Screen.GIFSearch.route) {
            GifSearchScreen(
                onGifSelected = { gifUrl ->
                    navController.navigate(Screen.Editor.createRoute(gifUrl = gifUrl))
                }
            )
        }
        composable(Screen.StickerSearch.route) {
            StickerSearchScreen(
                onStickerSelected = { stickerUrl ->
                    navController.navigate(Screen.Editor.createRoute(gifUrl = stickerUrl))
                }
            )
        }
    }
}
