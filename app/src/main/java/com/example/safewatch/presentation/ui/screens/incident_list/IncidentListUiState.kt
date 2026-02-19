package com.example.safewatch.presentation.ui.screens.incident_list

import com.example.safewatch.domain.model.Incident

sealed class IncidentListUiState {
    data object Loading : IncidentListUiState()
    data class Success(val items: List<Incident>) : IncidentListUiState()
    data class Error(val message: String) : IncidentListUiState()
    data object Empty : IncidentListUiState()
}