package com.example.mememaker.data.remote

import com.google.gson.annotations.SerializedName

data class TenorResponse(
    @SerializedName("results") val results: List<TenorResult>,
    @SerializedName("next") val next: String
)

data class TenorResult(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("media_formats") val mediaFormats: Map<String, MediaFormat>
)

data class MediaFormat(
    @SerializedName("url") val url: String,
    @SerializedName("dims") val dims: List<Int>,
    @SerializedName("size") val size: Int
)
