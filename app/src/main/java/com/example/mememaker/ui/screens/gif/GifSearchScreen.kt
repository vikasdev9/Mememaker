package com.example.mememaker.ui.screens.gif

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
fun GifSearchScreen(
    onGifSelected: (String) -> Unit,
    viewModel: GifViewModel = hiltViewModel()
) {
    val gifs by viewModel.gifs.collectAsState()
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
                CenterAlignedTopAppBar(title = { Text("Search GIFs") })
                TextField(
                    value = searchQuery,
                    onValueChange = { 
                        searchQuery = it
                        if (it.length > 2) viewModel.searchGifs(it)
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = { Text("Search Tenor...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true
                )
            }
        }
    ) { paddingValues ->
        if (isLoading && gifs.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(gifs) { gif ->
                    GifItem(gif, imageLoader) {
                        onGifSelected(gif.url)
                    }
                }
                item {
                    LaunchedEffect(Unit) {
                        viewModel.loadTrendingGifs()
                    }
                }
            }
        }
    }
}

@Composable
fun GifItem(gif: GifModel, imageLoader: ImageLoader, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(150.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = gif.previewUrl,
            contentDescription = gif.title,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
