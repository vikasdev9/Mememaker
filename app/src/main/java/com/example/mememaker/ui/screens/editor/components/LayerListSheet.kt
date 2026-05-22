package com.example.mememaker.ui.screens.editor.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mememaker.domain.model.MemeLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayerListSheet(
    layers: List<MemeLayer>,
    onToggleLock: (String) -> Unit,
    onToggleVisibility: (String) -> Unit,
    onDelete: (String) -> Unit,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Layers", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(layers.reversed()) { layer ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = when (layer) {
                        is MemeLayer.ImageLayer -> Icons.Default.Image
                        is MemeLayer.TextLayer -> Icons.Default.TextFields
                        is MemeLayer.StickerLayer -> Icons.Default.AddReaction
                        is MemeLayer.DrawingLayer -> Icons.Default.Brush
                    }
                    Icon(icon, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (layer is MemeLayer.TextLayer) layer.text else layer.javaClass.simpleName,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { onToggleVisibility(layer.id) }) {
                        Icon(
                            if (layer.isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Visibility"
                        )
                    }
                    IconButton(onClick = { onToggleLock(layer.id) }) {
                        Icon(
                            if (layer.isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                            contentDescription = "Lock"
                        )
                    }
                    IconButton(onClick = { onDelete(layer.id) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}
