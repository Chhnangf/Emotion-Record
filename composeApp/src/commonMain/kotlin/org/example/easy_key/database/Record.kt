package org.example.easy_key.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    // 记录年
    val year: Int,
    // 记录月，月份从1开始计数
    val month: Int,
    // 记录日，从1开始计数
    val day: Int,
    // 记录状态
    var status: String,
)

/**
 * about data block's information.
 * param:record is the date entity.
 * param:status is the status of the date.
 */

data class DataItemDetails(
    var record: DateEntity,

)

