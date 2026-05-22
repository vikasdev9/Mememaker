package com.example.mememaker.ui.screens.sticker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mememaker.domain.model.GifModel
import com.example.mememaker.domain.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StickerViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    private val _stickers = MutableStateFlow<List<GifModel>>(emptyList())
    val stickers: StateFlow<List<GifModel>> = _stickers.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var nextPos: String? = null

    init {
        loadTrendingStickers()
    }

    fun loadTrendingStickers() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTrendingStickers(nextPos).onSuccess { (newStickers, next) ->
                _stickers.value = _stickers.value + newStickers
                nextPos = next
            }
            _isLoading.value = false
        }
    }

    fun searchStickers(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _stickers.value = emptyList()
            nextPos = null
            repository.searchGifs(query, nextPos, "sticker").onSuccess { (newStickers, next) ->
                _stickers.value = newStickers
                nextPos = next
            }
            _isLoading.value = false
        }
    }
}
