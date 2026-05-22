package com.example.mememaker.di

import com.example.mememaker.data.repository.MemeRepositoryImpl
import com.example.mememaker.domain.repository.MemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMemeRepository(
        memeRepositoryImpl: MemeRepositoryImpl
    ): MemeRepository
}
