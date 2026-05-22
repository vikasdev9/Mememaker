package com.example.mememaker.ui.screens.editor.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImageAdjustSheet(
    brightness: Float,
    contrast: Float,
    onBrightnessChange: (Float) -> Unit,
    onContrastChange: (Float) -> Unit,
    onClose: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Adjust Image", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        
        Text("Brightness: ${String.format("%.2f", brightness)}")
        Slider(
            value = brightness,
            onValueChange = onBrightnessChange,
            valueRange = 0f..2f
        )
        
        Spacer(Modifier.height(8.dp))
        
        Text("Contrast: ${String.format("%.2f", contrast)}")
        Slider(
            value = contrast,
            onValueChange = onContrastChange,
            valueRange = 0f..2f
        )
        
        Spacer(Modifier.height(16.dp))
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("Done")
        }
    }
}
