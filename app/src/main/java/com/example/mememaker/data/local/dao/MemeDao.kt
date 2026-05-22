package com.example.mememaker.data.local.dao

import androidx.room.*
import com.example.mememaker.data.local.entity.FavoriteGifEntity
import com.example.mememaker.data.local.entity.RecentProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(gif: FavoriteGifEntity)

    @Delete
    suspend fun deleteFavorite(gif: FavoriteGifEntity)

    @Query("SELECT * FROM favorite_gifs")
    fun getAllFavorites(): Flow<List<FavoriteGifEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProject(project: RecentProjectEntity)

    @Query("SELECT * FROM recent_projects ORDER BY lastModified DESC")
    fun getAllProjects(): Flow<List<RecentProjectEntity>>

    @Query("DELETE FROM recent_projects WHERE id = :projectId")
    suspend fun deleteProject(projectId: Long)
}
