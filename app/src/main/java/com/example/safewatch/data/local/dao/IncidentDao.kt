package com.example.safewatch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safewatch.data.local.entity.IncidentEntity

@Dao
interface IncidentDao {

    @Query("SELECT * FROM incidents ORDER BY happenedAtEpochMillis DESC")
    suspend fun getAll(): List<IncidentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<IncidentEntity>)

    @Query("DELETE FROM incidents")
    suspend fun clearAll()


    @Query("SELECT * FROM incidents ORDER BY happenedAtEpochMillis DESC LIMIT :limit OFFSET :offset")
    suspend fun getPage(limit: Int, offset: Int): List<IncidentEntity>

    @Query("SELECT COUNT(*) FROM incidents")
    suspend fun count(): Int



}


