package com.example.safewatch.data.mapper

import com.example.safewatch.data.remote.dto.GenericDto
import com.example.safewatch.domain.model.Incident

/**
 * Maps generic API DTO → domain model.
 * Only data layer should know DTO structure.
 */
fun GenericDto.toDomain(): Incident {
    return Incident(
        id = id.toString(),
        happenedAtEpochMillis = System.currentTimeMillis(),
        cameraName = title,
        locationLabel = body
    )
}
