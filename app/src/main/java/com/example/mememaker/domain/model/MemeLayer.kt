package com.example.mememaker.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.UUID

sealed class MemeLayer(
    val id: String = UUID.randomUUID().toString(),
    var offset: Offset = Offset.Zero,
    var scale: Float = 1f,
    var rotation: Float = 0f,
    var isLocked: Boolean = false,
    var isVisible: Boolean = true
) {
    data class ImageLayer(
        val imagePath: String?,
        val gifUrl: String? = null,
        var brightness: Float = 1f,
        var contrast: Float = 1f,
        var saturation: Float = 1f,
        var maskPaths: List<DrawPath> = emptyList() // For background removal
    ) : MemeLayer()

    data class TextLayer(
        var text: String,
        var color: Color = Color.Black,
        var fontSize: Float = 24f,
        var fontStyle: String = "Default",
        var outlineColor: Color = Color.Transparent,
        var outlineWidth: Float = 0f,
        var shadowColor: Color = Color.Transparent,
        var shadowRadius: Float = 0f,
        var opacity: Float = 1f,
        var isCurved: Boolean = false
    ) : MemeLayer()

    data class StickerLayer(
        val stickerUrl: String
    ) : MemeLayer()

    data class DrawingLayer(
        val paths: List<DrawPath> = emptyList()
    ) : MemeLayer()
}

data class DrawPath(
    val points: List<androidx.compose.ui.geometry.Offset>,
    val color: androidx.compose.ui.graphics.Color,
    val strokeWidth: Float,
    val isEraser: Boolean = false
)
