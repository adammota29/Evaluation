package com.adam.evaluation.core.presentation.screen.location_list

import com.adam.evaluation.core.domain.usecase.GetLocationsUseCase
import com.adam.evaluation.core.domain.usecase.SyncLocationsUseCase
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val syncLocationsUseCase: SyncLocationsUseCase
) : ViewModel() {

    // L'état interne modifiable
    private val _state = MutableStateFlow(LocationListState())
    // L'état public exposé à la vue (immuable)
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    init {
        // On charge les données dès la création du ViewModel
        handleIntent(LocationListIntent.LoadLocations)
    }

    // L'unique point d'entrée pour la vue
    fun handleIntent(intent: LocationListIntent) {
        when (intent) {
            is LocationListIntent.LoadLocations -> observeLocations()
            is LocationListIntent.OnRefresh -> {
                refreshLocations()
            }
            is LocationListIntent.OnLocationClicked -> {
                // On gérera la navigation plus tard
                println("Location cliquée : ${intent.locationId}")
            }
        }
    }

    private fun observeLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            launch {
                // On collecte en continu la source de vérité locale.
                getLocationsUseCase().collect { locationsList ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            locations = locationsList
                        )
                    }
                }
            }

            try {
                syncLocationsUseCase()
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

    private fun refreshLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                syncLocationsUseCase()
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