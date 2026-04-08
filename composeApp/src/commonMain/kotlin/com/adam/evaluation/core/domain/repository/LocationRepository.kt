package com.adam.evaluation.core.domain.repository

import com.adam.evaluation.core.domain.model.Location
import kotlinx.coroutines.flow.Flow

/**
 * Contrat d'accès aux localisations pour la couche domaine.
 */
interface LocationRepository {
    /**
     * Observe la liste des localisations depuis la source de vérité locale.
     *
     * @return Un [Flow] émettant des listes de [Location].
     */
    fun getLocations(): Flow<List<Location>>

    /**
     * Observe une localisation depuis la source locale de vérité.
     *
     * @param id Identifiant de la localisation observée.
     * @return Un [Flow] émettant la [Location] ou `null` si absente.
     */
    fun observeLocationById(id: Int): Flow<Location?>

    /**
     * Force une synchronisation réseau vers la source locale (SSOT).
     *
     * @return `Unit`.
     */
    suspend fun refreshLocations()

    /**
     * Récupère une localisation spécifique via son identifiant.
     *
     * @param id Identifiant de la localisation à récupérer.
     * @return La [Location] trouvée, ou `null` si absente.
     */
    suspend fun getLocationById(id: Int): Location?
}