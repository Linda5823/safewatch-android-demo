package com.example.safewatch.domain.usecase

import android.content.Context
import com.example.safewatch.data.repository.IncidentRepository
import com.example.safewatch.domain.model.Incident

class GetIncidentsUseCase(
    context: Context
) {
    private val repository = IncidentRepository(context)

    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): List<Incident> {
        return repository.getIncidentsPageCacheFirst(page, pageSize)
    }
}




//class GetIncidentsUseCase(
//    context: Context
//) {
//    private val repository = PostRepository(context)
//
//    suspend operator fun invoke(): List<Incident> {
//        return repository.getIncidentsCacheFirst()
//    }
//}




/**
 * UseCase represents a business action.
 * It orchestrates data access, not the ViewModel.
 */
//class GetIncidentsUseCase(
//    private val repository: PostRepository = PostRepository()
//) {
//
//    suspend operator fun invoke(): List<Incident> {
//        return repository.fetchIncidents()
//    }
//}
