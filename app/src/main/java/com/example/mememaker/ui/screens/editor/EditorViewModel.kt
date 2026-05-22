package com.example.mememaker.ui.screens.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mememaker.domain.model.MemeLayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imagePath: String? = savedStateHandle["imagePath"]
    private val gifUrl: String? = savedStateHandle["gifUrl"]

    private val _layers = MutableStateFlow<List<MemeLayer>>(emptyList())
    val layers: StateFlow<List<MemeLayer>> = _layers.asStateFlow()

    private val _selectedLayerId = MutableStateFlow<String?>(null)
    val selectedLayerId: StateFlow<String?> = _selectedLayerId.asStateFlow()

    private val history = mutableListOf<List<MemeLayer>>()
    private var historyIndex = -1

    init {
        if (imagePath != null || gifUrl != null) {
            addLayer(MemeLayer.ImageLayer(imagePath, gifUrl))
        }
    }

    fun addLayer(layer: MemeLayer) {
        saveToHistory()
        _layers.value = _layers.value + layer
        _selectedLayerId.value = layer.id
    }

    fun updateLayerTransform(id: String, deltaOffset: Offset, deltaScale: Float, deltaRotation: Float) {
        _layers.value = _layers.value.map { layer ->
            if (layer.id == id && !layer.isLocked) {
                // Update properties directly or via copy if they were data classes with these properties
                // Since they are in the base class as 'var', we update them.
                layer.offset += deltaOffset
                layer.scale *= deltaScale
                layer.rotation += deltaRotation
                layer
            } else layer
        }
    }

    fun selectLayer(id: String?) {
        _selectedLayerId.value = id
    }

    fun deleteSelectedLayer() {
        val selectedId = _selectedLayerId.value ?: return
        saveToHistory()
        _layers.value = _layers.value.filter { it.id != selectedId }
        _selectedLayerId.value = null
    }

    fun updateLayerOrder(fromIndex: Int, toIndex: Int) {
        saveToHistory()
        val list = _layers.value.toMutableList()
        val item = list.removeAt(fromIndex)
        list.add(toIndex, item)
        _layers.value = list
    }

    fun toggleLayerLock(id: String) {
        _layers.value = _layers.value.map { layer ->
            if (layer.id == id) {
                layer.isLocked = !layer.isLocked
                layer
            } else layer
        }
    }

    fun toggleLayerVisibility(id: String) {
        _layers.value = _layers.value.map { layer ->
            if (layer.id == id) {
                layer.isVisible = !layer.isVisible
                layer
            } else layer
        }
    }

    fun updateTextProperties(
        id: String,
        color: Color? = null,
        fontSize: Float? = null,
        outlineColor: Color? = null,
        opacity: Float? = null
    ) {
        _layers.value = _layers.value.map { layer ->
            if (layer.id == id && layer is MemeLayer.TextLayer) {
                color?.let { layer.color = it }
                fontSize?.let { layer.fontSize = it }
                outlineColor?.let { layer.outlineColor = it }
                opacity?.let { layer.opacity = it }
                layer
            } else layer
        }
    }

    private fun saveToHistory() {
        // Simple history implementation
        if (historyIndex < history.size - 1) {
            // Remove future states if we are in the middle of history
            history.retainAll(history.subList(0, historyIndex + 1))
        }
        history.add(_layers.value.toList())
        historyIndex++
    }

    fun undo() {
        if (historyIndex >= 0) {
            _layers.value = history[historyIndex]
            historyIndex--
        }
    }
}
