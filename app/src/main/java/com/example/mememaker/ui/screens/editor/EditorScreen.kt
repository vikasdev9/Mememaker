package com.example.mememaker.ui.screens.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mememaker.domain.model.MemeLayer
import com.example.mememaker.util.rememberImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: EditorViewModel = hiltViewModel()
) {
    val layers by viewModel.layers.collectAsState()
    val selectedLayerId by viewModel.selectedLayerId.collectAsState()

    val imagePicker = rememberImagePicker { uri ->
        viewModel.addLayer(MemeLayer.ImageLayer(imagePath = uri.toString()))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Meme Editor") },
                actions = {
                    IconButton(onClick = { viewModel.undo() }) {
                        Icon(Icons.Default.Undo, contentDescription = "Undo")
                    }
                    IconButton(onClick = { /* Save/Export */ }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        },
        bottomBar = {
            EditorBottomBar(
                onAddText = { viewModel.addLayer(MemeLayer.TextLayer("New Text")) },
                onAddImage = { imagePicker() },
                onDelete = { viewModel.deleteSelectedLayer() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            // Main Canvas Area
            Box(
                modifier = Modifier
                    .size(400.dp) // Professional canvas size (aspect ratio handling later)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, _, _ ->
                            // Deselect if tapping background?
                        }
                    }
            ) {
                layers.forEach { layer ->
                    LayerComponent(
                        layer = layer,
                        isSelected = layer.id == selectedLayerId,
                        onSelect = { viewModel.selectLayer(layer.id) },
                        onTransform = { offset, scale, rotation ->
                            viewModel.updateLayerTransform(layer.id, offset, scale, rotation)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LayerComponent(
    layer: MemeLayer,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onTransform: (androidx.compose.ui.geometry.Offset, Float, Float) -> Unit
) {
    Box(
        modifier = Modifier
            .offset { 
                androidx.compose.ui.unit.IntOffset(layer.offset.x.toInt(), layer.offset.y.toInt()) 
            }
            .graphicsLayer(
                scaleX = layer.scale,
                scaleY = layer.scale,
                rotationZ = layer.rotation
            )
            .pointerInput(layer.id) {
                detectTransformGestures(panZoomLock = false) { _, pan, zoom, rotation ->
                    onTransform(pan, zoom, rotation)
                }
            }
            .clickable { onSelect() }
            .then(if (isSelected) Modifier.background(Color.Blue.copy(alpha = 0.1f)) else Modifier)
    ) {
        when (layer) {
            is MemeLayer.ImageLayer -> {
                AsyncImage(
                    model = layer.gifUrl ?: layer.imagePath,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )
            }
            is MemeLayer.TextLayer -> {
                Text(
                    text = layer.text,
                    color = layer.color,
                    fontSize = androidx.compose.ui.unit.TextUnit.Unspecified, // Fixed later
                    modifier = Modifier.padding(8.dp)
                )
            }
            is MemeLayer.StickerLayer -> {
                AsyncImage(
                    model = layer.stickerUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
            is MemeLayer.DrawingLayer -> {
                androidx.compose.foundation.Canvas(modifier = Modifier.size(200.dp)) {
                    val path = Path().apply {
                        if (layer.points.isNotEmpty()) {
                            moveTo(layer.points.first().x, layer.points.first().y)
                            layer.points.drop(1).forEach { lineTo(it.x, it.y) }
                        }
                    }
                    drawPath(
                        path = path,
                        color = layer.color,
                        style = Stroke(width = layer.strokeWidth, cap = StrokeCap.Round)
                    )
                }
            }
        }
    }
}

@Composable
fun EditorBottomBar(onAddText: () -> Unit, onAddImage: () -> Unit, onDelete: () -> Unit) {
    BottomAppBar {
        IconButton(onClick = onAddText) {
            Icon(Icons.Default.TextFields, contentDescription = "Add Text")
        }
        IconButton(onClick = onAddImage) {
            Icon(Icons.Default.Image, contentDescription = "Add Image")
        }
        IconButton(onClick = { /* Add Sticker */ }) {
            Icon(Icons.Default.AddReaction, contentDescription = "Add Sticker")
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

fun Modifier.clickableLayer(onClick: () -> Unit): Modifier = this.pointerInput(Unit) {
    // Custom click detection because transform gestures might consume events
}
