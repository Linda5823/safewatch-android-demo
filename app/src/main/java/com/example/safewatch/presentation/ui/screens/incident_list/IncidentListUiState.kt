package com.example.safewatch.presentation.ui.screens.incident_list

import com.example.safewatch.domain.model.Incident

sealed class IncidentListUiState {
    data object Loading : IncidentListUiState()
    data class Success(
        val items: List<Incident>,
        val isRefreshing: Boolean = false,
        val isLoadingMore: Boolean = false,
        val endReached: Boolean = false,
        val errorMessage: String? = null
    ) : IncidentListUiState()

    data class Error(val message: String) : IncidentListUiState()
    data object Empty : IncidentListUiState()
}