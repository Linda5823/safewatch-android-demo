package com.example.safewatch.data.mapper

import com.example.safewatch.data.local.entity.IncidentEntity
import com.example.safewatch.domain.model.Incident

fun IncidentEntity.toDomain(): Incident =
    Incident(
        id = id,
        happenedAtEpochMillis = happenedAtEpochMillis,
        cameraName = cameraName,
        locationLabel = locationLabel,
        licensePlate = licensePlate,
        imageUrl = imageUrl,
        confidence = confidence
    )

fun Incident.toEntity(): IncidentEntity =
    IncidentEntity(
        id = id,
        happenedAtEpochMillis = happenedAtEpochMillis,
        cameraName = cameraName,
        locationLabel = locationLabel,
        licensePlate = licensePlate,
        imageUrl = imageUrl,
        confidence = confidence
    )
