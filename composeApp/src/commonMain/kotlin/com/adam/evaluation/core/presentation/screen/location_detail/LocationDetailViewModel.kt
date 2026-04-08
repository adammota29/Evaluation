package com.adam.evaluation.core.presentation.screen.location_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adam.evaluation.core.domain.usecase.ObserveLocationByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    private val locationId: Int,
    private val observeLocationByIdUseCase: ObserveLocationByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocationDetailState())
    val state: StateFlow<LocationDetailState> = _state.asStateFlow()

    private var hasStarted = false

    init {
        handleIntent(LocationDetailIntent.Load)
    }

    fun handleIntent(intent: LocationDetailIntent) {
        when (intent) {
            is LocationDetailIntent.Load -> observeLocation()
        }
    }

    private fun observeLocation() {
        if (hasStarted) return
        hasStarted = true

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                observeLocationByIdUseCase(locationId).collect { location ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            location = location,
                            error = if (location == null) "Location introuvable" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Une erreur est survenue"
                    )
                }
            }
        }
    }
}

