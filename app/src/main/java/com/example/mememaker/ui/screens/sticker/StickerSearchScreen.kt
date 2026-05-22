package com.example.mememaker.ui.screens.sticker

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.mememaker.domain.model.GifModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickerSearchScreen(
    onStickerSelected: (String) -> Unit,
    viewModel: StickerViewModel = hiltViewModel()
) {
    val stickers by viewModel.stickers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text("Search Stickers") })
                TextField(
                    value = searchQuery,
                    onValueChange = { 
                        searchQuery = it
                        if (it.length > 2) viewModel.searchStickers(it)
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = { Text("Search Stickers...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true
                )
            }
        }
    ) { paddingValues ->
        if (isLoading && stickers.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // Stickers usually smaller
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(stickers) { sticker ->
                    StickerItem(sticker, imageLoader) {
                        onStickerSelected(sticker.url)
                    }
                }
                item {
                    LaunchedEffect(Unit) {
                        viewModel.loadTrendingStickers()
                    }
                }
            }
        }
    }
}

@Composable
fun StickerItem(sticker: GifModel, imageLoader: ImageLoader, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
    ) {
        AsyncImage(
            model = sticker.previewUrl,
            contentDescription = sticker.title,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
