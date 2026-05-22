package com.example.mememaker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_gifs")
data class FavoriteGifEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val previewUrl: String,
    val type: String // "gif" or "sticker"
)
