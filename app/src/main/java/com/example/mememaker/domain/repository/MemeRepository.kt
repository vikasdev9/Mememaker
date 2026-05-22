package com.example.mememaker.domain.repository

import com.example.mememaker.domain.model.GifModel
import kotlinx.coroutines.flow.Flow

interface MemeRepository {
    suspend fun getTrendingGifs(pos: String? = null): Result<Pair<List<GifModel>, String>>
    suspend fun searchGifs(query: String, pos: String? = null, searchFilter: String? = null): Result<Pair<List<GifModel>, String>>
    suspend fun getTrendingStickers(pos: String? = null): Result<Pair<List<GifModel>, String>>
}
