package com.appback.appbacksdk.database

import androidx.room.*
import com.appback.appbacksdk.poko.log.LogEvent

@Dao
interface LogEventDao {
    @Query("SELECT * FROM log_event")
    fun getAll(): List<LogEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(logEvents: LogEvent)

    @Delete
    fun delete(logEvent: LogEvent)
}