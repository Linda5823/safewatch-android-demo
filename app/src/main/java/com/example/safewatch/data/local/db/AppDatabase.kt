package com.example.safewatch.data.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.safewatch.data.local.dao.IncidentDao
import com.example.safewatch.data.local.entity.IncidentEntity

@Database(
    entities = [IncidentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao
}
