package com.appback.appbacksdk.database


import androidx.lifecycle.LiveData
import androidx.room.*
import com.appback.appbacksdk.poko.toggle.Toggle

@Dao
interface ToggleDao {
    @Query("SELECT * FROM toggle")
    fun getAll(): List<Toggle>

    @Query("SELECT * FROM toggle where  `key` = :key LIMIT 1")
    suspend fun findToggleAsync(key: String): Toggle

    @Query("SELECT * FROM toggle where `key` = :key LIMIT 1")
    fun findToogle(key: String): LiveData<Toggle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(translations: List<Toggle>)

    @Delete
    fun delete(toggle: Toggle)
}