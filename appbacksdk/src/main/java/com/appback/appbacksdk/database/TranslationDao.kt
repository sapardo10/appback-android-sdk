package com.appback.appbacksdk.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appback.appbacksdk.poko.transalation.Translation

@Dao
interface TranslationDao {
    @Query("SELECT * FROM translation")
    fun getAll(): List<Translation>

    @Query("SELECT * FROM translation where  `key` = :key LIMIT 1")
    suspend fun findTranslationAsync(key: String): Translation

    @Query("SELECT * FROM translation where `key` = :key LIMIT 1")
    fun findTranslation(key: String): LiveData<Translation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(translations: List<Translation>)

    @Delete
    fun delete(transalation: Translation)
}