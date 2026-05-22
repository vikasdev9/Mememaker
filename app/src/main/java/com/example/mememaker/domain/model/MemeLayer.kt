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
        val gifUrl: String? = null
    ) : MemeLayer()

    data class TextLayer(
        var text: String,
        var color: Color = Color.Black,
        var fontSize: Float = 24f,
        var fontStyle: String = "Default"
    ) : MemeLayer()

    data class StickerLayer(
        val stickerUrl: String
    ) : MemeLayer()
    data class DrawingLayer(
        val points: List<androidx.compose.ui.geometry.Offset>,
        val color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Black,
        val strokeWidth: Float = 5f
    ) : MemeLayer()
}
