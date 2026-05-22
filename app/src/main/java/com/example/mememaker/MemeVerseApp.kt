package com.example.mememaker

import android.app.Application
import com.example.mememaker.util.AdManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MemeVerseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AdManager.initialize(this)
    }
}
