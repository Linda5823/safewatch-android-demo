package com.example.safewatch.presentation.ui.screens.incident_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safewatch.data.repository.PostRepository
import com.example.safewatch.domain.model.Incident
import com.example.safewatch.domain.usecase.GetIncidentsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncidentListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<IncidentListUiState>(IncidentListUiState.Loading)

    val uiState: StateFlow<IncidentListUiState> = _uiState.asStateFlow()


    private val getIncidents = GetIncidentsUseCase()

    private fun load() {
        viewModelScope.launch {
            _uiState.value = IncidentListUiState.Loading
            try {
                // Suspend call must be done within a coroutine.
                val items = getIncidents()

                _uiState.value = if (items.isEmpty()) {
                    IncidentListUiState.Empty
                } else {
                    IncidentListUiState.Success(items)
                }

            } catch (t: Throwable) {
                _uiState.value = IncidentListUiState.Error(t.message ?: "Network error")
            }
        }
    }

    init {
        load()
    }

    fun refresh() {
        load()
    }



//---------------------The 3rd version:-------------------------

//    private val repo = PostRepository()
//
//
//    private fun load() {
//        viewModelScope.launch {
//            _uiState.value = IncidentListUiState.Loading
//            try {
//                // Suspend call must be done within a coroutine.
//                val items = repo.fetchIncidents()
//
//                _uiState.value = if (items.isEmpty()) {
//                    IncidentListUiState.Empty
//                } else {
//                    IncidentListUiState.Success(items)
//                }
//
//            } catch (t: Throwable) {
//                _uiState.value = IncidentListUiState.Error(t.message ?: "Network error")
//            }
//        }
//    }



//    init {
//        load()
//    }
//
//    fun refresh() {
//        load()
//    }




//---------------------The 2nd version:-------------------------

//    private val repo = PostRepository()
//
//
//    private fun load() {
//        viewModelScope.launch {
//            _uiState.value = IncidentListUiState.Loading
//            try {
//                val posts = repo.fetchPosts()
//
//                val items = posts.map {
//                    Incident(
//                        id = it.id.toString(),
//                        happenedAtEpochMillis = System.currentTimeMillis(),
//                        cameraName = it.title,
//                        locationLabel = it.body
//                    )
//                }
//
//                _uiState.value = if (items.isEmpty()) {
//                    IncidentListUiState.Empty
//                } else {
//                    IncidentListUiState.Success(items)
//                }
//
//            } catch (t: Throwable) {
//                _uiState.value = IncidentListUiState.Error(t.message ?: "Network error")
//            }
//        }
//    }


//    init {
//        load()
//    }
//
//    fun refresh() {
//        load()
//    }


//---------------------The 1st version:-------------------------

//    private fun load() {
//        viewModelScope.launch {
//            _uiState.value = IncidentListUiState.Loading
//            try {
//                delay(800) // simulate network
//                val items = fakeIncidents()
//                _uiState.value = when {
//                    items.isEmpty() -> IncidentListUiState.Empty
//                    else -> IncidentListUiState.Success(items)
//                }
//            } catch (t: Throwable) {
//                _uiState.value = IncidentListUiState.Error(t.message ?: "Unknown error")
//            }
//        }
//    }
//
//    private fun fakeIncidents(): List<Incident> {
//        val now = System.currentTimeMillis()
//        return listOf(
//            Incident(
//                id = "inc_1001",
//                happenedAtEpochMillis = now - 5 * 60_000,
//                cameraName = "North Gate",
//                locationLabel = "Parking Lot A",
//                licensePlate = "ABC1234",
//                imageUrl = null,
//                confidence = 0.92f,
//                tags = listOf("vehicle")
//            ),
//            Incident(
//                id = "inc_1002",
//                happenedAtEpochMillis = now - 42 * 60_000,
//                cameraName = "Main Entrance",
//                locationLabel = "Lobby",
//                licensePlate = null,
//                imageUrl = null,
//                confidence = null,
//                tags = listOf("person")
//            )
//        )
//    }

//    init {
//        load()
//    }
//
//    fun refresh() {
//        load()
//    }

}