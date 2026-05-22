package com.example.mememaker.di

import android.content.Context
import androidx.room.Room
import com.example.mememaker.data.local.MemeDatabase
import com.example.mememaker.data.local.dao.MemeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MemeDatabase {
        return Room.databaseBuilder(
            context,
            MemeDatabase::class.java,
            "meme_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMemeDao(database: MemeDatabase): MemeDao {
        return database.memeDao()
    }
}
