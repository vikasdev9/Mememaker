package com.example.mememaker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_projects")
data class RecentProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val lastModified: Long,
    val previewPath: String,
    val layerDataJson: String // Serialized MemeLayer list
)
