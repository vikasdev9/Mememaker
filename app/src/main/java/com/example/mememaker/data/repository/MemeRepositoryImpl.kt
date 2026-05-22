package com.example.mememaker.data.repository

import com.example.mememaker.data.remote.TenorApiService
import com.example.mememaker.domain.model.GifModel
import com.example.mememaker.domain.repository.MemeRepository
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val apiService: TenorApiService
) : MemeRepository {

    private val API_KEY = "LIVDSRZULEUB" // Default key or should be from build config

    override suspend fun getTrendingGifs(pos: String?): Result<Pair<List<GifModel>, String>> {
        return try {
            val response = apiService.getFeaturedGifs(apiKey = API_KEY, pos = pos)
            Result.success(response.results.map { it.toDomain() } to response.next)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchGifs(query: String, pos: String?): Result<Pair<List<GifModel>, String>> {
        return try {
            val response = apiService.searchGifs(query = query, apiKey = API_KEY, pos = pos)
            Result.success(response.results.map { it.toDomain() } to response.next)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTrendingStickers(pos: String?): Result<Pair<List<GifModel>, String>> {
        return try {
            val response = apiService.searchGifs(
                query = "trending",
                apiKey = API_KEY,
                pos = pos,
                searchFilter = "sticker"
            )
            Result.success(response.results.map { it.toDomain() } to response.next)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun com.example.mememaker.data.remote.TenorResult.toDomain(): GifModel {
        return GifModel(
            id = this.id,
            title = this.title,
            url = this.mediaFormats["gif"]?.url ?: "",
            previewUrl = this.mediaFormats["tinygif"]?.url ?: ""
        )
    }
}
