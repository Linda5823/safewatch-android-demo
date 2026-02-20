package com.example.safewatch.presentation.ui.screens.incident_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.safewatch.domain.model.Incident
import com.example.safewatch.domain.usecase.GetIncidentsUseCase
import com.example.safewatch.domain.usecase.RefreshIncidentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncidentListViewModel(app: Application) : AndroidViewModel(app) {
    private val pageSize = 20
    private var currentPage = 1
    private var endReached = false
    private var loadingMore = false

    private val refreshIncidents = RefreshIncidentsUseCase(app)
    private val getIncidents = GetIncidentsUseCase(app)

    private val _uiState = MutableStateFlow<IncidentListUiState>(IncidentListUiState.Loading)
    val uiState: StateFlow<IncidentListUiState> = _uiState.asStateFlow()

    init {
        loadFirstPage()
    }

    private fun setSuccess(
        items: List<Incident>,
        isRefreshing: Boolean = false,
        isLoadingMore: Boolean = false,
        endReached: Boolean = false,
        errorMessage: String? = null
    ) {
        _uiState.value = if (items.isEmpty()) {
            IncidentListUiState.Empty
        } else {
            IncidentListUiState.Success(items, isRefreshing, isLoadingMore, endReached, errorMessage)
        }
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _uiState.value = IncidentListUiState.Loading

            try {
                currentPage = 1
                endReached = false

                val firstPage = getIncidents(page = 1, pageSize = pageSize)
                endReached = firstPage.size < pageSize

                _uiState.value = if (firstPage.isEmpty()) {
                    IncidentListUiState.Empty
                } else {
                    IncidentListUiState.Success(
                        items = firstPage,
                        endReached = endReached
                    )
                }
            } catch (t: Throwable) {
                _uiState.value = IncidentListUiState.Error(t.message ?: "Load failed")
            }
        }
    }

    fun loadMore() {
        if (loadingMore || endReached) return

        val s = _uiState.value as? IncidentListUiState.Success ?: return
        if (s.isRefreshing || s.isLoadingMore) return

        viewModelScope.launch {
            loadingMore = true
            _uiState.value = s.copy(isLoadingMore = true, errorMessage = null)

            try {
                val nextPage = currentPage + 1
                val nextItems = getIncidents(page = nextPage, pageSize = pageSize)
                val reachedEnd = nextItems.size < pageSize
                val finalReachedEnd = reachedEnd || nextItems.isEmpty()

                val current = (_uiState.value as? IncidentListUiState.Success)?.items ?: s.items
                val merged = current + nextItems

                currentPage = nextPage
                endReached = finalReachedEnd

                _uiState.value = IncidentListUiState.Success(
                    items = merged,
                    isRefreshing = false,
                    isLoadingMore = false,
                    endReached = endReached,
                    errorMessage = null
                )
            } catch (t: Throwable) {
                val latest = _uiState.value as? IncidentListUiState.Success ?: s
                _uiState.value = latest.copy(
                    isLoadingMore = false,
                    errorMessage = t.message ?: "Load more failed"
                )
            } finally {
                loadingMore = false
            }
        }
    }

    fun refresh() {
        val s = _uiState.value as? IncidentListUiState.Success

        viewModelScope.launch {
            if (s != null) _uiState.value = s.copy(isRefreshing = true, errorMessage = null)

            try {
                refreshIncidents()

                currentPage = 1
                endReached = false
                loadingMore = false

                val firstPage = getIncidents(page = 1, pageSize = pageSize)
                endReached = firstPage.size < pageSize

                setSuccess(
                    items = firstPage,
                    isRefreshing = false,
                    isLoadingMore = false,
                    endReached = endReached,
                    errorMessage = null
                )
            } catch (t: Throwable) {
                if (s != null) {
                    _uiState.value = s.copy(isRefreshing = false, errorMessage = t.message ?: "Refresh failed")
                } else {
                    _uiState.value = IncidentListUiState.Error(t.message ?: "Refresh failed")
                }
            }
        }
    }
}
