package com.example.mememaker.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToEditor: (String?, String?) -> Unit,
    onNavigateToGIFs: () -> Unit,
    onNavigateToStickers: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("MemeVerse") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onNavigateToEditor(null, null) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Meme (Image)")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToGIFs,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("GIF Meme")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToStickers,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Stickers")
            }
        }
    }
}
