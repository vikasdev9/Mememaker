package com.example.mememaker.ui.screens.gif

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
class GifViewModel @Inject constructor(
    private val repository: MemeRepository
) : ViewModel() {

    private val _gifs = MutableStateFlow<List<GifModel>>(emptyList())
    val gifs: StateFlow<List<GifModel>> = _gifs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var nextPos: String? = null

    init {
        loadTrendingGifs()
    }

    fun loadTrendingGifs() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTrendingGifs(nextPos).onSuccess { (newGifs, next) ->
                _gifs.value = _gifs.value + newGifs
                nextPos = next
            }
            _isLoading.value = false
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _gifs.value = emptyList()
            nextPos = null
            repository.searchGifs(query, nextPos).onSuccess { (newGifs, next) ->
                _gifs.value = newGifs
                nextPos = next
            }
            _isLoading.value = false
        }
    }
}
