package com.adam.evaluation.core.domain.usecase

import com.adam.evaluation.core.domain.repository.LocationRepository

/**
 * Cas d'usage chargé de synchroniser les localisations distantes vers la base locale.
 *
 * @property repository Dépôt utilisé pour exécuter la synchronisation.
 */
class SyncLocationsUseCase(
    private val repository: LocationRepository
) {
    /**
     * Déclenche la synchronisation des localisations.
     *
     * @return `Unit`.
     */
    suspend operator fun invoke() {
        repository.refreshLocations()
    }
}
