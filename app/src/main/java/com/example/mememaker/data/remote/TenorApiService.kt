package com.example.mememaker.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TenorApiService {
    @GET("featured")
    suspend fun getFeaturedGifs(
        @Query("key") apiKey: String,
        @Query("limit") limit: Int = 20,
        @Query("media_filter") mediaFilter: String = "tinygif,gif",
        @Query("pos") pos: String? = null
    ): TenorResponse

    @GET("search")
    suspend fun searchGifs(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("limit") limit: Int = 20,
        @Query("media_filter") mediaFilter: String = "tinygif,gif",
        @Query("pos") pos: String? = null,
        @Query("searchfilter") searchFilter: String? = null // "sticker" for stickers
    ): TenorResponse
}
