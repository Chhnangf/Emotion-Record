package org.example.easy_key.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor



// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class EmotionDatabaseConstructor : RoomDatabaseConstructor<EmotionDatabase> {
    override fun initialize(): EmotionDatabase
}

@Database(entities = [DateEntity::class], version = 1)
@ConstructedBy(EmotionDatabaseConstructor::class)
abstract class EmotionDatabase: RoomDatabase() {
    abstract val recordDao: RecordDao
}

internal const val dbFileName = "my_room.db"



