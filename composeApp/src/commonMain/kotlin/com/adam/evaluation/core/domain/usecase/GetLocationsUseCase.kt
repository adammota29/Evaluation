package com.adam.evaluation.core.domain.usecase

import com.adam.evaluation.core.domain.repository.LocationRepository
import com.adam.evaluation.core.domain.model.Location
import kotlinx.coroutines.flow.Flow

/**
 * Expose le flux des localisations depuis la couche domaine.
 *
 * @property repository Dépôt qui fournit les localisations.
 */
class GetLocationsUseCase(
    private val repository: LocationRepository
) {
    /**
     * Récupère le flux des localisations disponibles.
     *
     * @return Un [Flow] émettant des listes de [Location].
     */
    operator fun invoke(): Flow<List<Location>> {
        return repository.getLocations()
    }
}