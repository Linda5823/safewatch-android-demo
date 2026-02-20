package com.example.safewatch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey val id: String,
    val happenedAtEpochMillis: Long,
    val cameraName: String,
    val locationLabel: String?,
    val licensePlate: String?,
    val imageUrl: String?,
    val confidence: Float?
)
