package org.example.easy_key.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(item: DateEntity)

    @Query("SELECT count(*) FROM RecordEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM RecordEntity")
    fun getAllAsFlow(): Flow<List<DateEntity>>
}