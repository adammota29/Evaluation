package com.adam.evaluation.core.presentation.screen.location_list

import com.adam.evaluation.core.domain.usecase.GetLocationsUseCase
import com.adam.evaluation.core.domain.usecase.SyncLocationsUseCase
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel de l'écran de liste des localisations.
 *
 * @property getLocationsUseCase Cas d'usage d'observation des localisations locales.
 * @property syncLocationsUseCase Cas d'usage de synchronisation réseau vers local.
 */
class LocationListViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val syncLocationsUseCase: SyncLocationsUseCase
) : ViewModel() {

    // L'état interne modifiable
    private val _state = MutableStateFlow(LocationListState())
    // L'état public exposé à la vue (immuable)
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LocationListEffect>()
    val effects: SharedFlow<LocationListEffect> = _effects

    init {
        // On charge les données dès la création du ViewModel
        handleIntent(LocationListIntent.LoadLocations)
    }

    /**
     * Point d'entrée unique pour traiter les actions de l'UI.
     *
     * @param intent Intention utilisateur à exécuter.
     * @return `Unit`.
     */
    fun handleIntent(intent: LocationListIntent) {
        when (intent) {
            is LocationListIntent.LoadLocations -> observeLocations()
            is LocationListIntent.OnRefresh -> {
                refreshLocations()
            }
            is LocationListIntent.OnLocationClicked -> {
                viewModelScope.launch {
                    _effects.emit(LocationListEffect.NavigateToDetail(intent.locationId))
                }
            }
        }
    }

    /**
     * Observe la source locale en continu puis déclenche une synchronisation initiale.
     *
     * @return `Unit`.
     */
    private fun observeLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            launch {
                // Collecte continue: chaque mise à jour DB rafraîchit automatiquement l'UI.
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
                // Premier sync après démarrage de la collecte pour alimenter la source locale.
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

    /**
     * Lance une synchronisation manuelle demandée par l'utilisateur.
     *
     * @return `Unit`.
     */
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