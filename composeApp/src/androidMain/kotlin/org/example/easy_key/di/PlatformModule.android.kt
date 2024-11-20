package org.example.easy_key.di

import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.easy_key.database.EmotionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<RoomDatabase.Builder<EmotionDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath("Emotion_database")
        Room.databaseBuilder<EmotionDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )

    }
}