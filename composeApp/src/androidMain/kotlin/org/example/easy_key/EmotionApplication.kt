package org.example.easy_key

import android.app.Application
import org.example.easy_key.di.platformModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EmotionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@EmotionApplication)
            modules(platformModule)
        }
    }
}