package com.example.mememaker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mememaker.data.local.dao.MemeDao
import com.example.mememaker.data.local.entity.FavoriteGifEntity
import com.example.mememaker.data.local.entity.RecentProjectEntity

@Database(
    entities = [FavoriteGifEntity::class, RecentProjectEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MemeDatabase : RoomDatabase() {
    abstract fun memeDao(): MemeDao
}
