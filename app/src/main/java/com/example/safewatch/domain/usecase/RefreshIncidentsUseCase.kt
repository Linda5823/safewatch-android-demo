package com.example.safewatch.domain.usecase

import android.content.Context
import com.example.safewatch.data.repository.IncidentRepository
import com.example.safewatch.domain.model.Incident

class RefreshIncidentsUseCase(
    context: Context
) {
    private val repository = IncidentRepository(context)

    suspend operator fun invoke(): List<Incident> {
        return repository.refreshIncidents()
    }
}

