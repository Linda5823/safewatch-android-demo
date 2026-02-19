package com.example.safewatch.data.mapper

import com.example.safewatch.data.remote.dto.PostDto
import com.example.safewatch.domain.model.Incident

/**
 * Maps network DTO → domain model.
 * Only data layer should know DTO structure.
 */

fun PostDto.toDomain(): Incident {
    return Incident(
        id = this.id.toString(),
        happenedAtEpochMillis = System.currentTimeMillis(),
        cameraName = this.title,
        locationLabel = this.body
    )
}