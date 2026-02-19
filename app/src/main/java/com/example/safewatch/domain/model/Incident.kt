package com.example.safewatch.domain.model

/**
 * Clean domain model used by UI.
 * Keep it free of Retrofit/Room annotations.
 */
data class Incident(
    val id: String,
    val happenedAtEpochMillis: Long,
    val cameraName: String,
    val locationLabel: String? = null,
    val licensePlate: String? = null,
    val imageUrl: String? = null,
    val confidence: Float? = null,
    val tags: List<String> = emptyList()
)
