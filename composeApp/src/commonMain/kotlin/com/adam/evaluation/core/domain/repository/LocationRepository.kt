package com.adam.evaluation.core.domain.repository

import com.adam.evaluation.core.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    /**
     * Retourne un Flow de la liste des lieux.
     */
    fun getLocations(): Flow<List<Location>>

    /**
     * Force une synchronisation réseau vers la source locale (SSOT).
     */
    suspend fun refreshLocations()

    /**
     * Récupère le détail d'un lieu spécifique.
     */
    suspend fun getLocationById(id: Int): Location?
}