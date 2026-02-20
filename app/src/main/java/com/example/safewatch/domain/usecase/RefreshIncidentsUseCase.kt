package com.example.safewatch.domain.usecase

import android.content.Context
import com.example.safewatch.data.repository.PostRepository
import com.example.safewatch.domain.model.Incident

class RefreshIncidentsUseCase(
    context: Context
) {
    private val repository = PostRepository(context)

    suspend operator fun invoke(): List<Incident> {
        return repository.refreshIncidents()
    }
}

