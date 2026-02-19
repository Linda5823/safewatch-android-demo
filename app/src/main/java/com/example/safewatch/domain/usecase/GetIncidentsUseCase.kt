package com.example.safewatch.domain.usecase

import com.example.safewatch.data.repository.PostRepository
import com.example.safewatch.domain.model.Incident

/**
 * UseCase represents a business action.
 * It orchestrates data access, not the ViewModel.
 */
class GetIncidentsUseCase(
    private val repository: PostRepository = PostRepository()
) {

    suspend operator fun invoke(): List<Incident> {
        return repository.fetchIncidents()
    }
}
